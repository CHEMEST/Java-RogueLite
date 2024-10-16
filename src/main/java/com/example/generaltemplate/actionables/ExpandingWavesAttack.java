package com.example.generaltemplate.actionables;

import com.example.generaltemplate.GameInfo;
import com.example.generaltemplate.Main;
import com.example.generaltemplate.Vector2D;
import com.example.generaltemplate.attacks.ExpandingWave;
import com.example.generaltemplate.entities.Entity;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;

public class ExpandingWavesAttack extends AttackComponent{
    private final double spreadRate;
    private final double maxSpread;
    private final int maxUpdatesPerWave;
    public ExpandingWavesAttack(Entity entity, double damage, double spreadRate, double maxSpread, int maxUpdatesPerWave) {
        super(entity, damage);
        this.maxSpread = maxSpread;
        this.spreadRate = spreadRate;
        this.maxUpdatesPerWave = maxUpdatesPerWave;
    }

    @Override
    public void action(GameInfo gI, Vector2D mouseLocation) {
        try {
            ExpandingWave expandingWave = new ExpandingWave(
                    getEntity().getLocation(),
                    new ImageView(new Image(new FileInputStream(Main.IMAGE_STARTER + "sampleProjectile.png"))),
                    "ExpandingWave", getEntity(), getDamage(), spreadRate, maxSpread, maxUpdatesPerWave);
            gI.getEntities().add(expandingWave);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
