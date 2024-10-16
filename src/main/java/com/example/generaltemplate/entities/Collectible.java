package com.example.generaltemplate.entities;

import com.example.generaltemplate.GameInfo;
import com.example.generaltemplate.Vector2D;
import javafx.scene.image.ImageView;

public abstract class Collectible extends Entity{

    public Collectible(Vector2D location, ImageView img, String type) {
        super(location, img, type);
    }
}
