package com.example.generaltemplate.detectables;

import com.example.generaltemplate.entities.Entity;
import com.example.generaltemplate.GameInfo;
import com.example.generaltemplate.Vector2D;

public class NoDetector extends DetectorComponent{
    public NoDetector(Entity entity, double size) {
        super(entity, size);
    }

    @Override
    public Entity detect(GameInfo gI, Vector2D mouseLocation) {
        return null;
    }
}
