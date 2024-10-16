package com.example.generaltemplate.actionables;

import com.example.generaltemplate.entities.Entity;

public abstract class ProjectileAttackComponent extends AttackComponent{
    private double bulletSpeed;
    public ProjectileAttackComponent(Entity entity, double damage, double bulletSpeed) {
        super(entity, damage);
        this.bulletSpeed = bulletSpeed;
    }

    public double getBulletSpeedBalanced() {
        return (Math.random() * bulletSpeed) + (bulletSpeed / 3);
    }

    public double getBulletSpeed() {
        return bulletSpeed;
    }
}
