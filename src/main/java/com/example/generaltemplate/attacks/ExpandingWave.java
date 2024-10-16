package com.example.generaltemplate.attacks;

import com.example.generaltemplate.AnimationPlayer;
import com.example.generaltemplate.GameInfo;
import com.example.generaltemplate.Vector2D;
import com.example.generaltemplate.entities.Entity;
import com.example.generaltemplate.moveables.NoMovement;
import javafx.scene.image.ImageView;

import static com.example.generaltemplate.Main.IMAGE_STARTER;

public class ExpandingWave extends Projectile{
    private final double maxSpread;
    private final double spreadRate;
    private final double startingSize;
    private double currentSize;
    public ExpandingWave(Vector2D location, ImageView img, String type, Entity entity, double damage, double spreadRate, double maxSpread, int maxUpdatesAlive) {
        super(location, img, type, entity, damage, new NoMovement(entity), null, maxUpdatesAlive);
        setAnimationPlayer(new AnimationPlayer(this, IMAGE_STARTER + "ExpandingWavesAnimations", "ExpandingWavesFrame", ".png" , 8, 15));
        this.maxSpread = maxSpread;
        this.spreadRate = spreadRate;
        this.currentSize = getImg().getImage().getWidth();
        this.startingSize = 0;
    }

    @Override
    public boolean updateLogic(GameInfo gI, Vector2D mouseLocation) {
        getAnimationPlayer().update(getImg().getImage().getWidth() + 128, gI.getDeltaTime() );
        return update(gI, mouseLocation, false);
    }
}
