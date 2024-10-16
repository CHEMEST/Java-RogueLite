package com.example.generaltemplate.entities;

import com.example.generaltemplate.Vector2D;
import javafx.scene.image.ImageView;

public class Enemy extends Actor{
    private double EXPDroppedOnKill;
    private final double batchSplitDenominator = 10;
    private final int value;

    public double getBatchSplitDenominator() {
        return batchSplitDenominator;
    }
    public int getValue() {
        return value;
    }
    public Enemy(Vector2D location, ImageView img, double maxHealth, String type, double expDroppedOnKill, int value) {
        super(location, img, maxHealth, type);
        this.value = value;
        EXPDroppedOnKill = expDroppedOnKill;
    }

    public double getEXPDroppedOnKill() {
        return EXPDroppedOnKill;
    }
    public int getEXPBatches(){
        return (int) (getEXPDroppedOnKill() / batchSplitDenominator) + 1;
    }

    public void setEXPDroppedOnKill(double EXPDroppedOnKill) {
        this.EXPDroppedOnKill = EXPDroppedOnKill;
    }

}
