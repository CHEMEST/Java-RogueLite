package com.example.generaltemplate.entities;

import com.example.generaltemplate.Vector2D;
import com.example.generaltemplate.entities.Entity;
import javafx.scene.image.ImageView;

public abstract class KillableEntity extends Entity {
    private double maxHealth;
    private double health;
    public KillableEntity(Vector2D location, ImageView img, double maxHealth, String type) {
        super(location, img, type);
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }
    public boolean isAlive() {return health > 0;}
    public void takeDamage(double damage){
        this.health -= damage;
    }

    public void heal(double healAmt){
        this.health += healAmt;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }
}
