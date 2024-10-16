package com.example.generaltemplate.moveables;

import com.example.generaltemplate.GameInfo;
import com.example.generaltemplate.Vector2D;
import com.example.generaltemplate.entities.Entity;

public class RotationMovement extends MovementComponent{
    private double angle = 0;
    private final Entity origin;
    public RotationMovement(Entity entity, Entity origin, double speed) {
        super(entity, speed);
        this.origin = origin;
    }

    @Override
    public boolean move(GameInfo gI, Vector2D mouseLocation) {
        angle += (getSpeed() * gI.getDeltaTime());
        getEntity().setLocation(origin.getLocation());
        getEntity().getImg().setRotate(angle);
        return false;
    }
}
