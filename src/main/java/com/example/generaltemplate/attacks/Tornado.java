package com.example.generaltemplate.attacks;

import com.example.generaltemplate.AnimationPlayer;
import com.example.generaltemplate.GameInfo;
import com.example.generaltemplate.Vector2D;
import com.example.generaltemplate.entities.Entity;
import com.example.generaltemplate.moveables.SetTargetMovement;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Scale;
import javafx.stage.Screen;

import static com.example.generaltemplate.Main.IMAGE_STARTER;

public class Tornado extends Projectile {
    Screen screen = Screen.getPrimary();
    private Scale scale = new Scale(1, 1);

/* TODO
    make the tornados flip
    make the tornados pick up entities and throw them after x updates {
        - entitiesPickedUp = array;
        - modifiy update() so
    }
*/
    public Tornado(Vector2D location, ImageView img, String type, Vector2D startingTarget, Entity entity, double damage, double speed) {
        super(location, img, type, entity, damage, null, null, 1000);
        setAnimationPlayer(new AnimationPlayer(this, IMAGE_STARTER + "TornadoAnimations", "tornadoAnimationFrame", ".png" , 2, 3));
        setMovementComponent(new SetTargetMovement(this, speed, startingTarget));
    }

    @Override
    public boolean updateLogic(GameInfo gI, Vector2D mouseLocation) {
        getAnimationPlayer().update(75, gI.getDeltaTime());
        if (checkCollisionsWithScreenBorder(this)){
            Vector2D newTarget = Vector2D.random(1, 1, screen.getBounds().getWidth(), screen.getBounds().getHeight());
            setMovementComponent(new SetTargetMovement(this, getMovementComponent().getSpeed(), newTarget));
        }
//        if (Math.random() > 0.9){
//            scale = new Scale(-1, 1);
//        }
        return update(gI, mouseLocation, true);
    }
}
