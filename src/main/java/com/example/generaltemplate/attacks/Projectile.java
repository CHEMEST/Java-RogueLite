package com.example.generaltemplate.attacks;

import com.example.generaltemplate.AnimationPlayer;
import com.example.generaltemplate.GameInfo;
import com.example.generaltemplate.Vector2D;
import com.example.generaltemplate.entities.Entity;
import com.example.generaltemplate.entities.KillableEntity;
import com.example.generaltemplate.moveables.MovementComponent;
import javafx.scene.image.ImageView;

import java.util.Objects;

public abstract class Projectile extends Entity {
    private MovementComponent movementComponent;
    private double damage;
    private final Entity entity;
    private int updatesAlive = 0;
    private final int maxUpdatesAlive;
    private AnimationPlayer animationPlayer;

    public Projectile(Vector2D location, ImageView img, String type, Entity entity, double damage, MovementComponent movementComponent, AnimationPlayer animationPlayer, int maxUpdatesAlive) {
        super(location, img, type);

        this.animationPlayer = animationPlayer;
        this.damage = damage;
        this.entity = entity;
        this.maxUpdatesAlive = maxUpdatesAlive;
        this.movementComponent = movementComponent;
    }
    public boolean update(GameInfo gI, Vector2D mouseLocation, boolean piercing){
        getMovementComponent().move(gI, mouseLocation);
        Entity entityCollidingWith = getEntityCollidingWith(gI);
        if (entityCollidingWith != null){
            ((KillableEntity) entityCollidingWith).takeDamage(this.getDamage());
            return !piercing;
        }
        updateAlive();
        return isOld();
    }
    public Entity getEntityCollidingWith(GameInfo gI){
        for (Entity entity : gI.getEntities()) {
            if (this.getEntity() != entity &&
                    entity instanceof KillableEntity &&
                    !Objects.equals(entity.getType(), this.getEntity().getType()) &&
                    entity.getImg().getBoundsInParent()
                            .intersects(this.getImg().getBoundsInParent())
            ) {
                return entity;
            }
        }
        return null;
    }

    public int getUpdatesAlive() {
        return updatesAlive;
    }

    public void updateAlive() {
        this.updatesAlive++;
    }
    public boolean isOld(){
        return updatesAlive > maxUpdatesAlive;
    }
    public int getMaxUpdatesAlive() {
        return maxUpdatesAlive;
    }

    public AnimationPlayer getAnimationPlayer() {
        return animationPlayer;
    }

    public void setAnimationPlayer(AnimationPlayer animationPlayer) {
        this.animationPlayer = animationPlayer;
    }

    public MovementComponent getMovementComponent() {
        return movementComponent;
    }

    public void setMovementComponent(MovementComponent movementComponent) {
        this.movementComponent = movementComponent;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public Entity getEntity() {
        return entity;
    }
}
