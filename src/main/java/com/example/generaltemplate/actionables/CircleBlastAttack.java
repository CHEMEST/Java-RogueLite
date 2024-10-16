package com.example.generaltemplate.actionables;

import com.example.generaltemplate.GameInfo;
import com.example.generaltemplate.Main;
import com.example.generaltemplate.Vector2D;
import com.example.generaltemplate.attacks.BasicProjectile;
import com.example.generaltemplate.entities.Entity;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;

public class CircleBlastAttack extends ProjectileAttackComponent{
    private static Image image;

    static {
        try{
            image = new Image(new FileInputStream(Main.IMAGE_STARTER + "sampleProjectile.png"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public CircleBlastAttack(Entity entity, double damage, double bulletSpeed) {
        super(entity, damage, bulletSpeed);
    }
    @Override
    public void action(GameInfo gI, Vector2D mouseLocation) {
        for (int i = 0; i < 360; i += 30){
//            System.out.println(i + ": " + getEntity().getLocation().rotate(i));
            BasicProjectile basicProjectile = new BasicProjectile(
                    getEntity().getLocation(),
                    new ImageView(image),
                    // why the actual fu does this make a generally circular shape???????
                    "Bullet", Vector2D.multiply(getEntity().getLocation().rotate(i), mouseLocation), getEntity(), getDamage(), getBulletSpeed());
            gI.getEntities().add(basicProjectile);
        }
    }
}
