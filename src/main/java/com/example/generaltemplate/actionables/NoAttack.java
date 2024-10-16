package com.example.generaltemplate.actionables;

import com.example.generaltemplate.entities.Entity;
import com.example.generaltemplate.GameInfo;
import com.example.generaltemplate.Vector2D;

public class NoAttack extends AttackComponent{
    public NoAttack(Entity entity) {
        super(entity, 0);
    }
    @Override
    public void action(GameInfo gI, Vector2D mouseLocation) {
    }

}
