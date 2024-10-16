package com.example.generaltemplate.moveables;

import com.example.generaltemplate.GameInfo;
import com.example.generaltemplate.Vector2D;

public interface Moveable {
    boolean move(GameInfo gI, Vector2D mouseLocation);
}
