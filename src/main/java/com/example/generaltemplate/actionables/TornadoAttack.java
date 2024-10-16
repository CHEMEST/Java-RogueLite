package com.example.generaltemplate.actionables;

import com.example.generaltemplate.GameInfo;
import com.example.generaltemplate.Vector2D;
import com.example.generaltemplate.attacks.Tornado;
import com.example.generaltemplate.entities.Entity;
import javafx.stage.Screen;

public class TornadoAttack extends ProjectileAttackComponent{
    private final int quantity;
    public TornadoAttack(Entity entity, double damage, double speed, int quantity) {
        super(entity, damage, speed);
        this.quantity = quantity;
    }

    @Override
    public void action(GameInfo gI, Vector2D mouseLocation) {
        Screen screen = Screen.getPrimary();
        for (int i = 0; i  < quantity;i++){
            Tornado tornado = new Tornado(
                    getEntity().getLocation(),
                    null,
                    "Bullet", Vector2D.random(1, 1, screen.getBounds().getWidth(), screen.getBounds().getHeight()), getEntity(), getDamage(), getBulletSpeedBalanced());
            gI.getEntities().add(tornado);
        }
    }

    public int getQuantity() {
        return quantity;
    }
}
