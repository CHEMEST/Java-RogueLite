package com.example.generaltemplate.moveables;

import com.example.generaltemplate.Component;
import com.example.generaltemplate.entities.Entity;

public abstract class MovementComponent extends Component implements Moveable {
    private double speed;
    public MovementComponent(Entity entity, double speed) {
        super(entity);
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
