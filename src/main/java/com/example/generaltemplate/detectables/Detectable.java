package com.example.generaltemplate.detectables;

import com.example.generaltemplate.entities.Entity;
import com.example.generaltemplate.GameInfo;
import com.example.generaltemplate.Vector2D;

public interface Detectable {
    Entity detect(GameInfo gI, Vector2D mouseLocation);
}
