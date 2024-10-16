package com.example.generaltemplate.moveables;

import com.example.generaltemplate.GameInfo;
import com.example.generaltemplate.Vector2D;
import com.example.generaltemplate.entities.Entity;

public class HomingMovement extends MovementComponent{
    private Entity target;
    public HomingMovement(Entity entity, double speed, Entity target) {
        super(entity, speed);
        this.target = target;
    }

    @Override
    public boolean move(GameInfo gI, Vector2D mouseLocation) {
        Vector2D directionToTarget = Vector2D.multiply(getEntity().getLocation().directionTo(target.getLocation()), new Vector2D(-1, -1));
        Vector2D velocity = Vector2D.multiply(directionToTarget, new Vector2D(getSpeed() * gI.getDeltaTime(), getSpeed() * gI.getDeltaTime()));
        getEntity().setLocation(Vector2D.add(getEntity().getLocation(), velocity));
        return false;
    }
}
