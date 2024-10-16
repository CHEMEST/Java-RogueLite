package com.example.generaltemplate.actionables;

import com.example.generaltemplate.GameInfo;
import com.example.generaltemplate.Main;
import com.example.generaltemplate.Vector2D;
import com.example.generaltemplate.attacks.BasicProjectile;
import com.example.generaltemplate.entities.Entity;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;

public class TargetProjectileAttack extends ProjectileAttackComponent{
    private Entity target;
    private static Image image;
    static{
        try{
            image = new Image(new FileInputStream(Main.IMAGE_STARTER + "sampleProjectile.png"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public TargetProjectileAttack(Entity entity, double damage, Entity target, double bulletSpeed) {
        super(entity, damage, bulletSpeed);
        this.target = target;
    }

    public void setTarget(Entity target) {
        this.target = target;
    }

    @Override
    public void action(GameInfo gI, Vector2D mouseLocation) {

        try {
            BasicProjectile basicProjectile = new BasicProjectile(
                    getEntity().getLocation(),
                    new ImageView(image),
                    "Bullet", target.getLocation(), getEntity(), getDamage(), getBulletSpeedBalanced());
            gI.getEntities().add(basicProjectile);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
