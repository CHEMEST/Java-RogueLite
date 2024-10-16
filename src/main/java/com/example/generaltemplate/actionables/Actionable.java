package com.example.generaltemplate.actionables;

import com.example.generaltemplate.GameInfo;
import com.example.generaltemplate.Vector2D;

public interface Actionable {
    void action(GameInfo gI, Vector2D mouseLocation);
}
