package com.example.generaltemplate.entities;

import com.example.generaltemplate.*;
import com.example.generaltemplate.actionables.*;
import com.example.generaltemplate.moveables.BulletSpeed;
import com.example.generaltemplate.moveables.PlayerSpeed;
import javafx.event.Event;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import static com.example.generaltemplate.Main.IMAGE_STARTER;

public class Player extends Actor{
    private static Player INSTANCE;
    private static final Image playerImage;
    private double currentEXP = 0;
    private int currentLevel = 1;
    private double nextLevelEXPRequirement = calculateNextLevelEXPRequirement(currentLevel);
    private boolean movingUp = false, movingDown = false, movingLeft = false, movingRight = false;
    private double speed;
    private int score = 0;
    private Vector2D mouseLocation;
    private final ArrayList<PlayerAbility> abilities = new ArrayList<>();
    private PlayerAbility currentPlayerAbility;

    static {
        try {
            playerImage = new Image(new FileInputStream(IMAGE_STARTER + "samplePlayer.png"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public double calculateNextLevelEXPRequirement(double level) {
        double numerator = 4 * (8 + (Math.pow(level, 3)) - (Math.pow(level, 2)));
        double denominator = 5;
        double b = 50;
        return (numerator / denominator) + b;
    }

    public double getNextLevelEXPRequirement() {
        return nextLevelEXPRequirement;
    }

    public double getCurrentEXP() {
        return currentEXP;
    }

    public void setCurrentEXP(double currentEXP) {
        this.currentEXP = currentEXP;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    private boolean firstUpdate = true;
    public static Player getInstance(GameInfo gI) {
        if (INSTANCE == null) {
            INSTANCE = new Player(
                    new Vector2D(500, 500),
                    new ImageView(playerImage),
                    100.0,
                    PlayerSpeed.MEDIUM.value(),
                    "Player",
                    gI
            );
        }
        if (!gI.getEntities().contains(INSTANCE) && INSTANCE.isAlive()) gI.getEntities().add(INSTANCE);
        return INSTANCE;
    }
    private Player(Vector2D location, ImageView img, double maxHealth, double speed, String type, GameInfo gI) {
        super(location, img, maxHealth, type);
        this.speed = speed;
//        abilities.add(new PlayerAbility(new CircleBlastAttack(this, 10, BulletSpeed.MEDIUM.value()), "sample 1", 1, 1));
//        abilities.add(new PlayerAbility(new RaygunAttack(this, 10, BulletSpeed.FAST.value(), mouseLocation), "sample 2", 1, 1));
//        abilities.add(new PlayerAbility(new TornadoAttack(this, 10, BulletSpeed.MEDIUM.value(), 3), "sample 3", 0, 0));
        abilities.add(new PlayerAbility(new ClearAllAttack(this), "sample 3", 1, 1));
        currentPlayerAbility = abilities.get(0);
    }
    public void keyPressed(KeyEvent event, GameInfo gI){
        switch (event.getCode()) {
            case W:
                movingUp = true;
                movingDown = false;
                break;
            case A:
                movingLeft = true;
                movingRight = false;
                break;
            case S:
                movingDown = true;
                movingUp = false;
                break;
            case D:
                movingRight = true;
                movingLeft = false;
                break;
            case SPACE:
                currentPlayerAbility.use(gI, mouseLocation);
                break;
            case E:
                currentPlayerAbility = nextAbility(currentPlayerAbility);
                System.out.println("Score: " + score);
                break;
            case Q:
                currentPlayerAbility = previousAbility(currentPlayerAbility);
                break;
        }
    }
    public void keyReleased(KeyEvent event, GameInfo gI){
        switch (event.getCode()) {
            case W:
                movingUp = false;
                break;
            case A:
                movingLeft = false;
                break;
            case S:
                movingDown = false;
                break;
            case D:
                movingRight = false;
                break;
        }
    }
    public ArrayList<PlayerAbility> getAbilities() {
        return abilities;
    }

    private PlayerAbility previousAbility(PlayerAbility currentPlayerAbility) {
        updateAbilityErrors();
        int index = abilities.indexOf(currentPlayerAbility);
        if (index == 0){
            return abilities.get(abilities.size() - 1);
        } else{
            return abilities.get(index - 1);
        }
    }

    private PlayerAbility nextAbility(PlayerAbility currentPlayerAbility) {
        updateAbilityErrors();
        int index = abilities.indexOf(currentPlayerAbility);
        System.out.println(abilities);
        if (index < abilities.size() - 1){
            return abilities.get(index + 1);
        } else {
            return abilities.get(0);
        }
    }

    private void updateAbilityErrors() {
        for (PlayerAbility ability : this.abilities){
            assert (ability.getAbilityComponent() != null);
            ability.getAbilityComponent().setEntity(this);
        }
    }

    public int getCurrentAbilityIndex(){
        return abilities.indexOf(currentPlayerAbility);
    }

    @Override
    public boolean updateLogic(GameInfo gI, Vector2D mouseLocation){

        this.mouseLocation = mouseLocation;
        if (firstUpdate){

            firstUpdate = false;
        }

        // attack functionality
        INSTANCE.setActionComponent(new TargetProjectileAttack(INSTANCE, 10, new Actor(mouseLocation, null, 1, "empty"), BulletSpeed.SLOW.value()), 0);

        gI.getRoot().getScene().setOnMouseClicked(event -> {
            getActionComponent().action(gI, mouseLocation);
        });

        // update functionality
        setTicksSinceLastUpdate(getTicksSinceLastUpdate() + 1);
        if (getTicksSinceLastUpdate() >= getTicksPerUpdate()){
            Vector2D velocity = new Vector2D(0, 0);
            if (movingUp) velocity = new Vector2D(velocity.x(), -1);
            if (movingDown) velocity = new Vector2D(velocity.x(), 1);
            if (movingLeft) velocity = new Vector2D(-1, velocity.y());
            if (movingRight) velocity = new Vector2D(1, velocity.y());
            this.incLocation(Vector2D.multiply(velocity, new Vector2D(speed * gI.getDeltaTime(), speed * gI.getDeltaTime() )));

            setTicksSinceLastUpdate(0);
        }

        //EXP
        ArrayList<Entity> entitiesCollidingWith = checkCollisions(gI, new String[]{});
        for (Entity entity: entitiesCollidingWith){
            if (entity instanceof EXPOrb){
                currentEXP += ((EXPOrb) entity).collect();
            }
        }

        if (currentEXP >= nextLevelEXPRequirement) {
            levelUp();
        }
//        System.out.println(currentEXP + " / " + nextLevelEXPRequirement);
        return !isAlive();
    }

    private void levelUp() {
        currentEXP = 0;
        nextLevelEXPRequirement = calculateNextLevelEXPRequirement(currentLevel);
        currentLevel++;
        this.setMaxHealth((int) this.getMaxHealth() * 1.25 );
        this.setHealth(getMaxHealth());
        System.out.println("You Leveled Up! Current level: " + currentLevel);
    }

    public void incScore(int inc){
        this.score += inc;
    }

    public int getScore() {
        return score;
    }

    public boolean willLevelUpFrom(Enemy enemy) {
        // WARNING: unsure if this actually works or not
//        System.out.println(enemy.getEXPDroppedOnKill());
        return nextLevelEXPRequirement <= currentEXP + enemy.getEXPDroppedOnKill();
    }
}
