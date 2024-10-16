package com.example.generaltemplate.moveables;

import com.example.generaltemplate.entities.Entity;
import com.example.generaltemplate.GameInfo;
import com.example.generaltemplate.Vector2D;

public class NoMovement extends MovementComponent{
    public NoMovement(Entity entity) {
        super(entity, 0);
    }

    @Override
    public boolean move(GameInfo gI, Vector2D mouseLocation) {
        return false;
    }
}
