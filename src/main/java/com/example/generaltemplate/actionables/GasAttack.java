package com.example.generaltemplate.actionables;

import com.example.generaltemplate.GameInfo;
import com.example.generaltemplate.Main;
import com.example.generaltemplate.Vector2D;
import com.example.generaltemplate.entities.Entity;
import com.example.generaltemplate.attacks.GasProjectile;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;

public class GasAttack extends ProjectileAttackComponent{
    private Entity target;
    private final double spreadRate;
    private final double maxSpread;
    private static Image image;
    static{
        try{
            image = new Image(new FileInputStream(Main.IMAGE_STARTER + "sampleProjectile.png"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public GasAttack(Entity entity, double damage, Entity target, double bulletSpeed, double spreadRate, double maxSpread) {
        super(entity, damage, bulletSpeed);
        this.target = target;
        this.maxSpread = maxSpread;
        this.spreadRate = spreadRate;
    }

    @Override
    public void action(GameInfo gI, Vector2D mouseLocation) {
        assert target != null;
        try {
            GasProjectile gasProjectile = new GasProjectile(
                    getEntity().getLocation(),
                    new ImageView(image),
                    "Bullet", target.getLocation(), getEntity(), getDamage(), getBulletSpeed(), spreadRate, maxSpread);
            gI.getEntities().add(gasProjectile);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
