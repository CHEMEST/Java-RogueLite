package com.example.generaltemplate.entities;

import com.example.generaltemplate.*;
import com.example.generaltemplate.actionables.*;
import com.example.generaltemplate.detectables.CircleDetector;
import com.example.generaltemplate.moveables.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.util.ArrayList;

import static com.example.generaltemplate.Main.IMAGE_STARTER;

public class EnemyFactory {
    private static EnemyFactory INSTANCE;
    private Image basicEnemyImage;
    private Image basicBossImage;
    private ArrayList<AbilityInfo> oldBossAbilities = new ArrayList<>();

    private EnemyFactory(){
        try{
            basicEnemyImage = new Image(new FileInputStream(IMAGE_STARTER+ "sampleActor.png"));
            basicBossImage = new Image(new FileInputStream(IMAGE_STARTER+ "spyder.png"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static EnemyFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EnemyFactory();
        }
        return INSTANCE;
    }
    public double balancedSpeed(double speedConst){
        return (Math.random() * speedConst) + (speedConst / ((Math.random() * 3) + 0.5));
    }
    public Enemy buildBasicEnemy(GameInfo gI){
        double width = gI.getRoot().getScene().getWidth();
        double height = gI.getRoot().getScene().getHeight();
        Enemy enemy = new Enemy(
                Vector2D.random(0, 0, width, height),
                new ImageView(basicEnemyImage),
                20.0,
                "Enemy", 5.0, 3);
        enemy.setMoveComponent(new HomingMovement(enemy, balancedSpeed(EnemySpeed.SLOW.value()), Player.getInstance(gI)), 0)
//                .setActionComponent(new ShieldAction(enemy, 5, 0, 0), 0)
                .setActionComponent(new TargetProjectileAttack(enemy, EnemyDamage.LOW.value(), Player.getInstance(gI), BulletSpeed.MEDIUM.value()), 100)
                .setDetectComponent(new CircleDetector(enemy, 60), 0);
        gI.getEntities().add(enemy);
        return enemy;
    }
    public Enemy buildMeleeEnemy(GameInfo gI){
        double width = gI.getRoot().getScene().getWidth();
        double height = gI.getRoot().getScene().getHeight();
        Enemy enemy = new Enemy(
                Vector2D.random(0, 0, width, height),
                new ImageView(basicEnemyImage),
                20.0,
                "Enemy", 20.0, 16);
        enemy.setMoveComponent(new HomingMovement(enemy, balancedSpeed(EnemySpeed.MEDIUM.value()), Player.getInstance(gI)), 0)
                .setActionComponent(new MeleeAttack(enemy, EnemyDamage.LOW.value(), 150.0), 100)
                .setDetectComponent(new CircleDetector(enemy, 80), 0);
        gI.getEntities().add(enemy);
        return enemy;
    }
    public Enemy buildGrowEnemy(GameInfo gI){
        double width = gI.getRoot().getScene().getWidth();
        double height = gI.getRoot().getScene().getHeight();
        Enemy enemy = new Enemy(
                Vector2D.random(0, 0, width, height),
                new ImageView(basicEnemyImage),
                20.0,
                "Enemy", 10.0, 8);
        enemy.setMoveComponent(new HomingMovement(enemy, balancedSpeed(EnemySpeed.SLOW.value()), Player.getInstance(gI)), 0)
                .setActionComponent(new GrowAttack(enemy, EnemyDamage.LOW.value(), Player.getInstance(gI), BulletSpeed.SLOW.value(), 30.0, 65.0), 100)
                .setDetectComponent(new CircleDetector(enemy, 150), 0);
        gI.getEntities().add(enemy);
        return enemy;
    }
    public Enemy buildGasEnemy(GameInfo gI){
        double width = gI.getRoot().getScene().getWidth();
        double height = gI.getRoot().getScene().getHeight();
        Enemy enemy = new Enemy(
                Vector2D.random(0, 0, width, height),
                new ImageView(basicEnemyImage),
                20.0,
                "Enemy", 10.0, 6);
        enemy.setMoveComponent(new HomingMovement(enemy, balancedSpeed(EnemySpeed.SLOW.value()), Player.getInstance(gI)), 0)
                .setActionComponent(new GasAttack(enemy, EnemyDamage.LOW.value(), Player.getInstance(gI), BulletSpeed.SLOW.value(), 500.0, 45.0), 200)
                .setDetectComponent(new CircleDetector(enemy, 150), 0);
        gI.getEntities().add(enemy);
        return enemy;
    }


    public Enemy buildWaveEnemy(GameInfo gI) {
        double width = gI.getRoot().getScene().getWidth();
        double height = gI.getRoot().getScene().getHeight();
        Enemy enemy = new Enemy(
                Vector2D.random(0, 0, width, height),
                new ImageView(basicEnemyImage),
                20.0,
                "Enemy", 5.0, 19);
        enemy.setMoveComponent(new HomingMovement(enemy, balancedSpeed(EnemySpeed.SLOW.value()), Player.getInstance(gI)), 0)
                .setActionComponent(new ExpandingWavesAttack(enemy, EnemyDamage.LOW.value(), 10.0, 1000.0, 200), 100)
                .setDetectComponent(new CircleDetector(enemy, 60), 0);
        gI.getEntities().add(enemy);
        return enemy;
    }

    public Boss buildSpiderBoss(GameInfo gI) {
        double width = gI.getRoot().getScene().getWidth();
        double height = gI.getRoot().getScene().getHeight();
        AbilityInfo abilityInfo = new AbilityInfo(null ,null, null, null);
        double expNeeded = Player.getInstance(gI).getNextLevelEXPRequirement() - Player.getInstance(gI).getCurrentEXP();
        Boss boss = new Boss(
                Vector2D.random(0, 0, width, height),
                new ImageView(basicBossImage),
                100.0,
                "spiderBoss",  expNeeded,
                oldBossAbilities,
                abilityInfo,
                160
                );
        abilityInfo.setActionComponent(new CircleBlastAttack(boss, EnemyDamage.LOW.value(), BulletSpeed.MEDIUM.value()))
                        .setActionUpdater(new Updater(100))
                                .setMoveComponent(new HomingMovement(boss, EnemySpeed.SLOW.value(), Player.getInstance(gI)))
                                        .setMoveUpdater(new Updater(0));
        oldBossAbilities.add(abilityInfo);
        gI.getEntities().add(boss);
        return boss;
    }

    public Boss buildRayBoss(GameInfo gI) {
        double width = gI.getRoot().getScene().getWidth();
        double height = gI.getRoot().getScene().getHeight();
        AbilityInfo abilityInfo = new AbilityInfo(null ,null, null, null);
        double expNeeded = Player.getInstance(gI).getNextLevelEXPRequirement() - Player.getInstance(gI).getCurrentEXP();
        Boss boss = new Boss(
                Vector2D.random(0, 0, width, height),
                new ImageView(basicBossImage),
                100.0,
                "rayBoss",  expNeeded,
                oldBossAbilities,
                abilityInfo,
                450
        );
        abilityInfo.setActionComponent(new RaygunAttack(boss, EnemyDamage.LOW.value(), BulletSpeed.MEDIUM.value(), Player.getInstance(gI)))
                .setActionUpdater(new Updater(75))
                .setMoveComponent(new HomingMovement(boss, EnemySpeed.SLOW.value(), Player.getInstance(gI)))
                .setMoveUpdater(new Updater(0));
        oldBossAbilities.add(abilityInfo);
        gI.getEntities().add(boss);
        return boss;
    }

    public Boss buildTornadoBoss(GameInfo gI) {
        double width = gI.getRoot().getScene().getWidth();
        double height = gI.getRoot().getScene().getHeight();
        AbilityInfo abilityInfo = new AbilityInfo(null ,null, null, null);
        double expNeeded = Player.getInstance(gI).getNextLevelEXPRequirement() - Player.getInstance(gI).getCurrentEXP();
        Boss boss = new Boss(
                Vector2D.random(0, 0, width, height),
                new ImageView(basicBossImage),
                250.0,
                "tornadoBoss",  expNeeded,
                oldBossAbilities,
                abilityInfo,
                850
        );
        abilityInfo.setActionComponent(new TornadoAttack(boss, 1, BulletSpeed.MEDIUM.value(), 3))
                .setActionUpdater(new Updater(200))
                .setMoveComponent(new HomingMovement(boss, EnemySpeed.SLOW.value(), Player.getInstance(gI)))
                .setMoveUpdater(new Updater(0));
        oldBossAbilities.add(abilityInfo);
        gI.getEntities().add(boss);
        return boss;
    }
}
