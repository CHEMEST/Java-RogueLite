package com.example.generaltemplate.entities;

import com.example.generaltemplate.GameInfo;
import com.example.generaltemplate.Vector2D;
import com.example.generaltemplate.moveables.BulletSpeed;
import com.example.generaltemplate.moveables.HomingMovement;
import com.example.generaltemplate.moveables.MovementComponent;
import com.example.generaltemplate.moveables.NoMovement;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static com.example.generaltemplate.Main.IMAGE_STARTER;

public class EXPOrb extends Collectible {
    private byte buffer = 1;
    private MovementComponent movementComponent = new NoMovement(this);
    private boolean collected = false;
    private double speed = BulletSpeed.FAST.value();
    private final double value;
    private static final Image img;
    static {
        try {
            img = new Image(new FileInputStream(IMAGE_STARTER + "sampleProjectile.png"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public EXPOrb(Vector2D location, String type, double value, Entity target) {
        super(location,
            null, type);
        setImg(new ImageView(img));
        this.value = value;
        if (target != null){
            movementComponent = new HomingMovement(this, speed, target);
        }
    }

    @Override
    public boolean updateLogic(GameInfo gI, Vector2D mouseLocation) {
        movementComponent.move(gI, mouseLocation);
        movementComponent.setSpeed(speed);
        if (speed < 20.0){
            int mod = Math.random() > 0.5 ? 4 : 5;
            speed += (speed / mod);
        }
        return collected;
    }
    public double collect(){
        if (buffer <= 0){
            collected = true;
            return value;
        }
        buffer--;
        return 0;
    }
}
