package com.example.generaltemplate.moveables;

import com.example.generaltemplate.Vector2D;
import com.example.generaltemplate.entities.Entity;

public abstract class TargetedMovementComponent extends MovementComponent{
    private Vector2D target;
    public TargetedMovementComponent(Entity entity, double speed, Vector2D target) {
        super(entity, speed);
        this.target = target;
    }

    public Vector2D getTarget() {
        return target;
    }

    public void setTarget(Vector2D target) {
        this.target = target;
    }
}
