package com.example.generaltemplate.actionables;

import com.example.generaltemplate.Component;
import com.example.generaltemplate.GameInfo;
import com.example.generaltemplate.Vector2D;
import com.example.generaltemplate.entities.Entity;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ShieldAction extends Component implements Actionable{
    // oops, this should be in the Attacks area, guess I was a bit excited and did part of it here mb.
    private Rectangle rect = new Rectangle();
    private int rotationDegrees = 0;
    private int rotationSpeed;
    private int shieldCount;
    private double shieldThickness;
    public ShieldAction(Entity entity, int rotationSpeed, int shieldCount, double shieldThickness) {
        super(entity);
        this.rotationSpeed = rotationSpeed;
        rect.setHeight(shieldThickness);
        rect.setWidth(shieldCount / 3.0);
        rect.setFill(Color.WHITE);
    }

    @Override
    public void action(GameInfo gI, Vector2D mouseLocation) {
        Vector2D entityLoc = getEntity().getLocation();
        Vector2D myLoc = entityLoc.rotate(rotationDegrees += rotationSpeed);
        rect.setLayoutX(myLoc.x());
        rect.setLayoutY(myLoc.y());
//        rect.setRotate(rotationDegrees += rotationSpeed);

        gI.getRoot().getChildren().add(rect);
    }

}
