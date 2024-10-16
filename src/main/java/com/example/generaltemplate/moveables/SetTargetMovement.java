package com.example.generaltemplate.moveables;

import com.example.generaltemplate.GameInfo;
import com.example.generaltemplate.Vector2D;
import com.example.generaltemplate.entities.Entity;

public class SetTargetMovement extends TargetedMovementComponent{
    public SetTargetMovement(Entity entity, double speed, Vector2D target) {
        super(entity, speed, target);
        setTarget(Vector2D.multiply(getEntity().getLocation().directionTo(target), new Vector2D(-1, -1)));
    }
    @Override
    public boolean move(GameInfo gI, Vector2D mouseLocation) {
        assert getTarget() != null;
        Vector2D velocity = new Vector2D(getSpeed() * gI.getDeltaTime(), getSpeed() * gI.getDeltaTime());
        Vector2D difference = Vector2D.multiply(getTarget(), velocity);
        Vector2D updatedLocation = Vector2D.add(getEntity().getLocation(), difference);
        getEntity().setLocation(updatedLocation);
        return false;
    }
}
