package com.example.generaltemplate;

import com.example.generaltemplate.actionables.Actionable;
import com.example.generaltemplate.actionables.CircleBlastAttack;
import com.example.generaltemplate.actionables.RaygunAttack;
import com.example.generaltemplate.actionables.TornadoAttack;
import com.example.generaltemplate.attacks.Melee;
import com.example.generaltemplate.attacks.Projectile;
import com.example.generaltemplate.entities.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Main extends Application {
    // player / UI
    private Player player;
    private final ProgressBar levelBar = new ProgressBar(0);
    private final ProgressBar healthBar = new ProgressBar(1);
    private final HBox playerAbilitiesHBox = new HBox();
    private final Text aliveTimerDisplayText = new Text();
    private final Text scoreDisplayText = new Text();
    private ImageView playerActiveAbilityImage;
    private boolean displayingGUI = true;
    private final ProgressBar bossHPBar = new ProgressBar(1);
        // pause menu
        private final Text pausedText = new Text();
        private boolean pauseMenuActive = false;
    // main game
    private Group root = new Group();
    private Scene scene = new Scene(root, 1200, 800, Color.BLACK);
    private Stage stage = new Stage();

    private final ArrayList<Entity> entities = new ArrayList<>();
    private Vector2D mouseLocation = new Vector2D(0, 0);
    private GameInfo gameInfo = new GameInfo(entities, root, player, -1);

    public static final String IMAGE_STARTER = "src/main/resources/Pictures/";
    private final Random random = new Random();
    private double viewportWidth = scene.getWidth();
    private double viewportHeight = scene.getHeight();
    private boolean updatingEntities = true;
    // intro
    private final SingleAnimation intro = new SingleAnimation(null);
    private final SingleAnimation outro = new SingleAnimation(null);
    {
        outro.setAnimationPlayer(new AnimationPlayer(outro, IMAGE_STARTER + "outroAnimation", "outro", ".png" , 10, 4));
        intro.setAnimationPlayer(new AnimationPlayer(intro, IMAGE_STARTER + "introAnimation", "introFrame", ".png" , 8, 4));
    }


    // time
    private int updates = 0;
    private long timeAfterLastUpdate = System.nanoTime();
    private long lastTimeS = 0;
    private final long nanoToSeconds = 1_000_000_000;
    private int bossTimerSeconds = 0, runningSeconds = 0, introTimerSecondsLeft = 2;
    // enemies
    private final EnemyFactory enemyFactory = EnemyFactory.getInstance();
//    private boolean spawnedEnemyThisInterval = false;
    private boolean bossActive = false, bossTimerRunning = true;
    private boolean spiderBossSpawned = false, rayBossSpawned = false, tornadoBossSpawned = false;
    private AbilityInfo spiderBossAbility = null, rayBossAbility = null, tornadoBossAbility = null;

    /*
    Todo:
        POLISH
        - organize GUI
        - death menu, score, restart, and high score (score = time alive &or bosses killed)
        - intro/end animation (Metal doors opening/closing?)
        IMAGES
        - boss images
        - enemy images
        - exp image(s) (just like a gold/diamond frame with silver inside, then rotate it 90deg)
        - background
        - remove smoothing of images (nearest neighbor scaling)
        ABILITIES
        - add locks to locked abilities
        - add a purple squircle for active ability instead of red square
        - make actual images for each ability
        - give them a grey square that gets shorter as the ability cools down

        OPTIMIZE
        - use threads when updating UI vs entities (just use 'em in general or for abilities)
        - use deltaTime in AnimationPlayer (see 4-18-24AM AnimationPlayer)
        - Preload all the images in AnimationPlayer instead of building a new one each frame (see an old version?)

    Extras:
        - display enemy health using a key bind
        - more bosses / enemies
        - Tags | Instead of using string as tags, it would be better to have a system of enums where you can have like inheritance between em, I made that in the Tag enum but incorporating it into this project is honestly not what I wanna do rn

     */

    public void init() {
        player = Player.getInstance(gameInfo);
        intro.start();
//        outro.start();

        Image cursorImg = null;
        try{
            cursorImg = new Image(new FileInputStream(IMAGE_STARTER + "crossHair.png"));
            playerActiveAbilityImage = new ImageView(new Image(new FileInputStream( IMAGE_STARTER + "hurtSpaceCropped.png" )));
        }catch (Exception e){
//            public static final String ANSI_RED = "\u001B[31m";
//            System.out.println(ANSI_RED + e.getMessage());
            // could also use a Logger

            e.printStackTrace();
        }
        Cursor cursor = new ImageCursor(cursorImg, 0, 0);
        scene.setCursor(cursor);

    }

    @Override
    public void start(Stage primaryStage) {
//        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Hello!");

        levelBar.setStyle("-fx-accent: green; -fx-control-inner-background: #024415; -fx-text-box-border: black;");
        levelBar.setPrefSize(700.0, 40.0);

        bossHPBar.setStyle("-fx-accent: #830630; -fx-control-inner-background: #440219; -fx-text-box-border: black;");
        bossHPBar.setPrefSize(800.0, 50.0);

        healthBar.setStyle("-fx-accent: #b90514; -fx-control-inner-background: #80000b; -fx-text-box-border: black;");
        healthBar.setPrefSize(350.0, 45.0);


        try {
            playerAbilitiesHBox.getChildren().addAll(
                    new ImageView(new Image(new FileInputStream(IMAGE_STARTER + "BasicProjectileAnimations/projectileFrame3.png"))),
                    new ImageView(new Image(new FileInputStream(IMAGE_STARTER + "GrowingProjectileAnimations/growingProjectileFrame1.png"))),
                    new ImageView(new Image(new FileInputStream(IMAGE_STARTER + "TornadoAnimations/tornadoAnimationFrame1.png"))),
                    new ImageView(new Image(new FileInputStream(IMAGE_STARTER + "GasProjectileAnimations/gasProjectileFrame9.png")))
            );

        } catch (Exception e){
            e.printStackTrace();
        }

        for (Node node : playerAbilitiesHBox.getChildren()){
            if (node instanceof ImageView) {
                double size = 48.0;
                ((ImageView) node).setFitHeight(size );
                ((ImageView) node).setFitWidth(size );
            }
        }

        playerAbilitiesHBox.setSpacing(15.0);
        playerActiveAbilityImage.setOpacity(0.5);
        playerActiveAbilityImage.setFitWidth(48.0);
        playerActiveAbilityImage.setFitHeight(48.0);


        Font aliveTimerFont = null;
        Font pausedTextFont = null;
        Font scoreTextFont = null;
        try{
            aliveTimerFont = Font.loadFont(new FileInputStream(IMAGE_STARTER + "Warzone.ttf"), 33);
            pausedTextFont = Font.loadFont(new FileInputStream(IMAGE_STARTER + "Warzone.ttf"), 100);
            scoreTextFont = Font.loadFont(new FileInputStream(IMAGE_STARTER + "Warzone.ttf"), 30);
        } catch (Exception ignored){}
        aliveTimerDisplayText.setFont(aliveTimerFont);
        aliveTimerDisplayText.setFill(Color.LIME);

        pausedText.setFont(pausedTextFont);
        pausedText.setFill(Color.GRAY);
        pausedText.setText("PAUSED");

        scoreDisplayText.setFont(scoreTextFont);
        scoreDisplayText.setFill(Color.LIGHTSEAGREEN);
        scoreDisplayText.setText("Score: 0000");

        updateViewportHorizontal(scene.getWidth());
        updateViewPortVertical(scene.getHeight());
        scene.setOnMouseMoved(event -> {
            double x = event.getSceneX();
            double y = event.getSceneY();
            this.mouseLocation = new Vector2D(x, y);
//            System.out.println("Mouse coordinates: (" + x + ", " + y + ")") ;
        });

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastTimeS >= nanoToSeconds) {
//                    spawnedEnemyThisInterval = false;
                    if (updatingEntities) {
                        runningSeconds++;
                        introTimerSecondsLeft--;
                    }
                    if (bossTimerRunning){
                        bossTimerSeconds++;
                    }
                    lastTimeS = now;
                }

                long currentTime = System.nanoTime();
                double deltaTime = (currentTime - timeAfterLastUpdate) / 1e9; // Convert nanoseconds to seconds
                gameInfo.setDeltaTime(deltaTime);
                if (!intro.isRunning() && !outro.isRunning()){
                    update();
                    updateUI();
                    trySpawnEnemies();
                    updates++;
                } else if (intro.isRunning()){
                    System.out.println("intro playing");
                    root.getChildren().clear();
                    intro.updateFullScreen(viewportWidth, viewportHeight);
                    root.getChildren().add(intro.getImg());
                } else if (outro.isRunning()){
                    System.out.println("outro playing");
                    root.getChildren().clear();
                    outro.updateFullScreen(viewportWidth, viewportHeight);
                    root.getChildren().add(outro.getImg());
                }

                timeAfterLastUpdate = System.nanoTime();
//                System.out.println(player.getLocation());
            }
        }.start();

        scene.widthProperty().addListener((observable, oldValue, newValue) -> {
            viewportWidth = newValue.doubleValue();
            updateViewportHorizontal(viewportWidth);
        });

        scene.heightProperty().addListener((observable, oldValue, newValue) -> {
            viewportHeight = newValue.doubleValue();
            updateViewPortVertical(viewportHeight);
        });

        scene.setOnKeyPressed(event -> {
            Player.getInstance(gameInfo).keyPressed(event, gameInfo);
            switch (event.getCode()) {
                case ESCAPE:
                    pauseMenuActive = !pauseMenuActive;
//                    outro.start();
                    break;
            }
        });
        scene.setOnKeyReleased(event -> {
            Player.getInstance(gameInfo).keyReleased(event, gameInfo);
        });
    }

    private void updateViewportHorizontal(double viewportWidth) {
        outro.setFitWidthChange((int) (viewportWidth - outro.getImg().getImage().getWidth()) + 200);
        pausedText.setLayoutX((viewportWidth / 2) - 225);
        aliveTimerDisplayText.setLayoutX((viewportWidth) - 245);
        scoreDisplayText.setLayoutX((viewportWidth) - 275);
        levelBar.setLayoutX( (viewportWidth/2) - 350 );
        bossHPBar.setLayoutX((viewportWidth/2) - 400);
        double playerAbilitiesSize = playerAbilitiesHBox.getChildren().size();
        double playerAbilitiesHBoxSpacing = playerAbilitiesHBox.getSpacing();
        playerAbilitiesHBox.setLayoutX((viewportWidth/2) - (playerAbilitiesSize*48.0 + (playerAbilitiesSize*playerAbilitiesHBoxSpacing))/2); // 48 is the size of each ability image
        entities.forEach(e -> e.updateViewport(viewportWidth, viewportHeight));
    }
    private void updateViewPortVertical(double viewportHeight){
        outro.setFitWidthChange((int) (viewportHeight - outro.getImg().getImage().getHeight()) + 200);
        aliveTimerDisplayText.setLayoutY(viewportHeight - 10);
        scoreDisplayText.setLayoutY(scoreDisplayText.getFont().getSize() + 5 );
        pausedText.setLayoutY( (viewportHeight / 2) );
        levelBar.setLayoutY( viewportHeight - 45 );
        bossHPBar.setLayoutY( 60 );
        playerAbilitiesHBox.setLayoutY( viewportHeight - 45 - 60 );
        entities.forEach(e -> e.updateViewport(viewportWidth, viewportHeight));
    }
    private void updatePauseMenu(){
        root.getChildren().add(pausedText);
//        root.getChildren().add(highScoreText);
    }
    private void updateUI() {
        int minutes = runningSeconds / 60;
        int seconds = runningSeconds % 60;
        String minutesS = Integer.toString(minutes).length() > 1 ? Integer.toString(minutes) : "0" + minutes;
        String secondsS = Integer.toString(seconds).length() > 1 ? Integer.toString(seconds) : "0" + seconds;
        if (Player.getInstance(gameInfo).isAlive()) aliveTimerDisplayText.setText("Alive: " + minutesS + ":" + secondsS);


        levelBar.setProgress(player.getCurrentEXP() / player.getNextLevelEXPRequirement());

        healthBar.setProgress(player.getHealth() / player.getMaxHealth() );
        healthBar.setLayoutX( 0 );
        healthBar.setLayoutY( 0 );

        String score = Player.getInstance(gameInfo).getScore() + "";
        String zeros = "000000";
        if (score.length() <= zeros.length()) zeros = zeros.substring(score.length());
        else zeros = "";
        scoreDisplayText.setText("Score: " + zeros + Player.getInstance(gameInfo).getScore());

        // Boss Bar
        boolean bossActive = false;
        for (Entity e : entities){
            if (e instanceof Boss) {
                bossActive = true;
                bossHPBar.setProgress(((Boss) e).getHealth() / ((Boss) e).getMaxHealth());
                break;
            }
        }
        bossHPBar.setVisible(bossActive);

        Node node = playerAbilitiesHBox.getChildren().get(Player.getInstance(gameInfo).getCurrentAbilityIndex());
        if (node instanceof ImageView){
            int i = playerAbilitiesHBox.getChildren().indexOf(node);
            double spacing = playerAbilitiesHBox.getSpacing() * i;
            playerActiveAbilityImage.setLayoutX(playerAbilitiesHBox.getLayoutX() + spacing + (i * ((ImageView) node).getFitWidth()));
            playerActiveAbilityImage.setLayoutY(playerAbilitiesHBox.getLayoutY());
        }
        if (pauseMenuActive) {updatePauseMenu(); updatingEntities = false;}
    }

    private void trySpawnEnemies() {
//        System.out.println(Player.getInstance(gameInfo).getCurrentLevel());
        if (!bossActive && Player.getInstance(gameInfo).getCurrentLevel() > 1){
            if (!spiderBossSpawned && bossTimerSeconds > 10){
                Boss boss = enemyFactory.buildSpiderBoss(gameInfo);
                spiderBossSpawned = true;
                bossActive = true;
                bossTimerRunning = false;
                spiderBossAbility = boss.getSpecialAbility();
            }
            else if (!rayBossSpawned && bossTimerSeconds > 20){
                Boss boss = enemyFactory.buildRayBoss(gameInfo);
                rayBossSpawned = true;
                bossActive = true;
                bossTimerRunning = false;
                rayBossAbility = boss.getSpecialAbility();
            }
            else if (!tornadoBossSpawned && bossTimerSeconds > 30){
                Boss boss = enemyFactory.buildTornadoBoss(gameInfo);
                tornadoBossSpawned = true;
                bossActive = true;
                bossTimerRunning = false;
                tornadoBossAbility = boss.getSpecialAbility();
            }
        }
        // for the enemy on seconds instead of updates, this might be helpful:
        // && !spawnedEnemyThisInterval
        int lvl = Player.getInstance(gameInfo).getCurrentLevel();
        int spawnRate = 110 - lvl*50;
        spawnRate = spawnRate > 30 ? spawnRate : 40 - lvl;
        if (!bossActive && updates != 0 && updates % spawnRate == 0 ) {
//            spawnedEnemyThisInterval = true;
            int totalWeight = 12;
            int randNum = random.nextInt(totalWeight) + 1;

            if (randNum <= 3) {
                enemyFactory.buildBasicEnemy(gameInfo);
            } else if (randNum <= 5) {
                enemyFactory.buildWaveEnemy(gameInfo);
            } else if (randNum <= 7) {
                enemyFactory.buildMeleeEnemy(gameInfo);
            } else if (randNum <= 10) {
                enemyFactory.buildGrowEnemy(gameInfo);
            } else if (randNum <= 12) {
                enemyFactory.buildGasEnemy(gameInfo);
            }
        }

    }

    private void update() {
        root.getChildren().clear();
        for (int e = entities.size() - 1; e >= 0; e--) {
            Entity entity = entities.get(e);
            entity.updateVisuals();
            if (updatingEntities) { // && Player.getInstance(gameInfo).isAlive()
                root.getChildren().add(entity.getImg());
                if (entity.updateLogic(gameInfo, mouseLocation)) {
                    if (entity instanceof Projectile || entity instanceof Melee || entity instanceof EXPOrb) System.out.print("");
                    else System.out.println("Removed Entity: " + entity.toString());
                    if (entity instanceof Enemy) {
                        for (int i = 0; i < ((Enemy) entity).getEXPBatches(); i++) {
                            double x = (Math.random() * 80) - 40;
                            double y = (Math.random() * 80) - 40;
                            Vector2D loc = Vector2D.add(entity.getLocation(), new Vector2D(x, y));
                            double expValue = ((Enemy) entity).getBatchSplitDenominator();
                            entities.add(new EXPOrb(loc, "EXPOrb", expValue, player));
                        }
                        Player.getInstance(gameInfo).incScore(((Enemy) entity).getValue());
                    }
                    if (entity instanceof Boss) {
                        this.bossActive = false;
                        bossTimerRunning = true;
                        player = Player.getInstance(gameInfo);
                        if (Objects.equals(entity.getType(), "spiderBoss")) {
                            Actionable ability = getSpiderAbility();
                            Player.getInstance(gameInfo).getAbilities().add(new PlayerAbility(ability, "Circle Blast", 3, 0));
                        } else if (Objects.equals(entity.getType(), "rayBoss")) {
                            Actionable ability = getRaygunAbility();
                            Player.getInstance(gameInfo).getAbilities().add(new PlayerAbility(ability, "Raygun", 2, 0));
                        } else if (Objects.equals(entity.getType(), "tornadoBoss")) {
                            Actionable ability = getTornadoAbility();
                            Player.getInstance(gameInfo).getAbilities().add(new PlayerAbility(ability, "Tornado Summon", 10, 0));
                        }
                    }
                    if (entity instanceof Player){
                        System.out.println("player killed");
                        outro.start();
                    }
                    entities.remove(entities.get(e));
                }
            }
        }
        if (displayingGUI){
            root.getChildren().add(playerAbilitiesHBox);
            root.getChildren().add(playerActiveAbilityImage);
            root.getChildren().add(healthBar);
            root.getChildren().add(bossHPBar);
            root.getChildren().add(levelBar);
            root.getChildren().add(aliveTimerDisplayText);
            root.getChildren().add(scoreDisplayText);
        }
        if (updates % 20 == 0) scene.setFill(Color.BLACK);
        updatingEntities = true;
    }
    private Actionable getSpiderAbility() {
        assert spiderBossAbility != null && spiderBossAbility.getActionComponent() instanceof CircleBlastAttack;
        CircleBlastAttack oldAtk = (CircleBlastAttack) spiderBossAbility.getActionComponent();
        return new CircleBlastAttack(player, oldAtk.getDamage(), oldAtk.getBulletSpeed());
    }
    private Actionable getRaygunAbility() {
        assert rayBossAbility != null && rayBossAbility.getActionComponent() instanceof RaygunAttack;
        RaygunAttack oldAtk = (RaygunAttack) rayBossAbility.getActionComponent();
        return new RaygunAttack(player, oldAtk.getDamage(), oldAtk.getBulletSpeed(), mouseLocation);
    }
    private Actionable getTornadoAbility() {
        assert tornadoBossAbility != null && tornadoBossAbility.getActionComponent() instanceof TornadoAttack;
        TornadoAttack oldAtk = (TornadoAttack) tornadoBossAbility.getActionComponent();
        return new TornadoAttack(player, oldAtk.getDamage(), oldAtk.getBulletSpeed(), oldAtk.getQuantity());
    }

    public static void main(String[] args) {
        launch();
    }
}