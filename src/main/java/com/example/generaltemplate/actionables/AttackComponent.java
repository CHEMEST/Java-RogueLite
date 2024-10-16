package com.example.generaltemplate.actionables;

import com.example.generaltemplate.Component;
import com.example.generaltemplate.entities.Entity;

public abstract class AttackComponent extends Component implements Actionable {
    private final double damage;
    public AttackComponent(Entity entity, double damage) {
        super(entity);
        this.damage = damage;
    }

    public double getDamage() {
        return damage;
    }
}
