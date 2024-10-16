package com.example.generaltemplate.actionables;

import com.example.generaltemplate.GameInfo;
import com.example.generaltemplate.Main;
import com.example.generaltemplate.Vector2D;
import com.example.generaltemplate.entities.Entity;
import com.example.generaltemplate.attacks.Melee;
import com.example.generaltemplate.entities.KillableEntity;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;

public class MeleeAttack extends AttackComponent{
    private final double rotationSpeed;
    public MeleeAttack(Entity entity, double damage, double rotationSpeed) {
        super(entity, damage);
        this.rotationSpeed = rotationSpeed;
    }

    @Override
    public void action(GameInfo gI, Vector2D mouseLocation) {
        try {
            KillableEntity myEntity = (KillableEntity) getEntity();
            myEntity.heal(10);
            Melee attack = new Melee(
                    getEntity().getLocation(),
                    new ImageView(new Image(new FileInputStream(Main.IMAGE_STARTER + "sampleSword.png"))),
                    "Attack", getEntity(), getDamage(), rotationSpeed);
            gI.getEntities().add(attack);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
