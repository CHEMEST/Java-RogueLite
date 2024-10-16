package com.example.generaltemplate.actionables;

import com.example.generaltemplate.GameInfo;
import com.example.generaltemplate.Main;
import com.example.generaltemplate.Vector2D;
import com.example.generaltemplate.entities.Entity;
import com.example.generaltemplate.attacks.GrowingProjectile;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;

public class GrowAttack extends ProjectileAttackComponent{
    private Entity target;
    private final double spreadRate;
    private final double maxSpread;
    public GrowAttack(Entity entity, double damage, Entity target, double bulletSpeed, double spreadRate, double maxSpread) {
        super(entity, damage, bulletSpeed);
        this.target = target;
        this.maxSpread = maxSpread;
        this.spreadRate = spreadRate;
    }

    @Override
    public void action(GameInfo gI, Vector2D mouseLocation) {
        assert target != null;
        try {
            GrowingProjectile growingProjectile = new GrowingProjectile(
                    getEntity().getLocation(),
                    new ImageView(new Image(new FileInputStream(Main.IMAGE_STARTER + "sampleProjectile.png"))),
                    "Bullet", target.getLocation(), getEntity(), getDamage(), getBulletSpeed(), spreadRate, maxSpread);
            gI.getEntities().add(growingProjectile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
