package com.example.generaltemplate.attacks;

import com.example.generaltemplate.GameInfo;
import com.example.generaltemplate.Vector2D;
import com.example.generaltemplate.entities.Entity;
import com.example.generaltemplate.entities.KillableEntity;
import com.example.generaltemplate.moveables.MovementComponent;
import com.example.generaltemplate.moveables.RotationMovement;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class Melee extends Entity {
    private final MovementComponent movementComponent;
    private final double damage;
    private final Entity entity;
    private int updatesAlive = 0;

    public Melee(Vector2D location, ImageView img, String type, Entity entity, double damage, double speed) {
        super(location, img, type);
        this.damage = damage;
        this.entity = entity;
        this.movementComponent = new RotationMovement(this, entity, speed);
    }

    @Override
    public boolean updateLogic(GameInfo gI, Vector2D mouseLocation) {
        movementComponent.move(gI, mouseLocation);
        for (Entity entity : gI.getEntities()){
            if ( this.entity != entity &&
                    entity instanceof KillableEntity &&
                    !Objects.equals(entity.getType(), this.entity.getType()) &&
                    entity.getImg().getBoundsInParent()
                            .intersects( this.getImg().getBoundsInParent() )
            ){
                ((KillableEntity) entity).takeDamage(this.damage);
                return true;
            }
        }
        updatesAlive++;
        int maxUpdatesAlive = 500;
        if (!gI.getEntities().contains(entity)) return true;
        return (updatesAlive > maxUpdatesAlive);
    }

}
