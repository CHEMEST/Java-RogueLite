package com.example.generaltemplate.attacks;

import com.example.generaltemplate.AnimationPlayer;
import com.example.generaltemplate.GameInfo;
import com.example.generaltemplate.Vector2D;
import com.example.generaltemplate.entities.Entity;
import com.example.generaltemplate.moveables.SetTargetMovement;
import javafx.scene.image.ImageView;

import static com.example.generaltemplate.Main.IMAGE_STARTER;

public class BasicProjectile extends Projectile {
    public BasicProjectile(Vector2D location, ImageView img, String type, Vector2D target, Entity entity, double damage, double speed) {
        super(location, img, type, entity, damage, null, null, 200);
        setAnimationPlayer(new AnimationPlayer(this, IMAGE_STARTER + "BasicProjectileAnimations", "projectileFrame", ".png" , 3, 5));
        setMovementComponent(new SetTargetMovement(this, speed, target));
    }

    @Override
    public boolean updateLogic(GameInfo gI, Vector2D mouseLocation) {
        getAnimationPlayer().update(15, gI.getDeltaTime());
        return update(gI, mouseLocation, false);
    }

}
