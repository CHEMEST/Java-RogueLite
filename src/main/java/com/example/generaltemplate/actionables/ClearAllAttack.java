package com.example.generaltemplate.actionables;

import com.example.generaltemplate.GameInfo;
import com.example.generaltemplate.Vector2D;
import com.example.generaltemplate.entities.EXPOrb;
import com.example.generaltemplate.entities.Entity;
import com.example.generaltemplate.entities.KillableEntity;
import com.example.generaltemplate.entities.Player;
import javafx.scene.paint.Color;

import java.util.Objects;

public class ClearAllAttack extends AttackComponent{
    public ClearAllAttack(Entity entity) {
        super(entity, Integer.MAX_VALUE);
    }

    @Override
    public void action(GameInfo gI, Vector2D mouseLocation) {
        gI.getRoot().getScene().setFill(Color.RED);
        for (Entity entity : gI.getEntities()) {
            if (entity instanceof KillableEntity &&
                    !(Objects.equals(entity.getType(), this.getEntity().getType()))
            ){
                ((KillableEntity) entity).setHealth( -1 );
            }
        }
    }
}
