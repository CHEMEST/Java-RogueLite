package com.example.generaltemplate.actionables;

import com.example.generaltemplate.GameInfo;
import com.example.generaltemplate.Main;
import com.example.generaltemplate.Vector2D;
import com.example.generaltemplate.attacks.BasicProjectile;
import com.example.generaltemplate.entities.Entity;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;

public class RaygunAttack extends ProjectileAttackComponent{
    private static Image image;
    private Entity targetEntity;
    private Vector2D targetLocation;
    static {
        try{
            image = new Image(new FileInputStream(Main.IMAGE_STARTER + "sampleProjectile.png"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public RaygunAttack(Entity entity, double damage, double bulletSpeed, Entity target) {
        super(entity, damage, bulletSpeed);
//        System.out.println(target);
        this.targetEntity = target;
    }
    public RaygunAttack(Entity entity, double damage, double bulletSpeed, Vector2D target) {
        super(entity, damage, bulletSpeed);
        this.targetLocation = target;
    }
    @Override
    public void action(GameInfo gI, Vector2D mouseLocation) {
//        System.out.println(targetEntity);
        Vector2D target = targetEntity != null ? targetEntity.getLocation() : mouseLocation;

        for (int i = 0; i < 10; i += 1){
//            System.out.println(i + ": " + getEntity().getLocation().rotate(i));
            BasicProjectile basicProjectile = new BasicProjectile(
                    getEntity().getLocation(),
                    new ImageView(image),
                    "Bullet", target, getEntity(), getDamage(), getBulletSpeedBalanced());
            gI.getEntities().add(basicProjectile);
        }
    }
}
