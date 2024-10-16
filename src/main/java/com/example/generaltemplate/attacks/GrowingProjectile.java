package com.example.generaltemplate.attacks;

import com.example.generaltemplate.AnimationPlayer;
import com.example.generaltemplate.GameInfo;
import com.example.generaltemplate.Vector2D;
import com.example.generaltemplate.entities.Entity;
import com.example.generaltemplate.moveables.SetTargetMovement;
import javafx.scene.image.ImageView;

import static com.example.generaltemplate.Main.IMAGE_STARTER;

public class GrowingProjectile extends Projectile {

    private final double maxSpread;
    private final double spreadRate;
    private final double startingSize;
    private double currentSize;

    public GrowingProjectile(Vector2D location, ImageView img, String type, Vector2D target, Entity entity, double damage, double speed, double spreadRate, double maxSpread) {
        super(location, img, type, entity, damage, null, null, 200);
        setAnimationPlayer(new AnimationPlayer(this, IMAGE_STARTER + "GrowingProjectileAnimations", "growingProjectileFrame", ".png" , 4, 5));
        setMovementComponent(new SetTargetMovement(this, speed, target));
        this.maxSpread = maxSpread;
        this.spreadRate = spreadRate;
        this.currentSize = getImg().getImage().getWidth();
        this.startingSize = 0; // getImg().getImage().getWidth()
    }


    @Override
    public boolean updateLogic(GameInfo gI, Vector2D mouseLocation) {
        if (currentSize < (maxSpread + startingSize)) {
            currentSize += spreadRate * gI.getDeltaTime();
        }
        getAnimationPlayer().update( currentSize, gI.getDeltaTime() );



        return update(gI, mouseLocation, false);
    }
}
