package com.example.generaltemplate.detectables;

import com.example.generaltemplate.entities.Entity;
import com.example.generaltemplate.GameInfo;
import com.example.generaltemplate.Vector2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CircleDetector extends DetectorComponent {
    private Circle detectionCircle = new Circle();

    public CircleDetector(Entity entity, double size) {
        super(entity, size);
        detectionCircle.setRadius(getSize());
        detectionCircle.setFill(Color.GRAY);
    }


    @Override
    public Entity detect(GameInfo gI, Vector2D mouseLocation) {
//        gI.root().getChildren().remove(this.detectionCircle);
        detectionCircle.setCenterX(this.getEntity().getLocation().x());
        detectionCircle.setCenterY(this.getEntity().getLocation().y());
        for (Entity entity : gI.getEntities()){
            if ( entity != this.getEntity() &&
                    entity.getImg().getBoundsInParent()
                    .intersects( detectionCircle.getBoundsInParent() )
            ){
                return entity;
            }
        }
//        gI.root().getChildren().add(this.detectionCircle);
        return null;

    }
}
