package com.example.generaltemplate;

import com.example.generaltemplate.entities.Actor;
import com.example.generaltemplate.entities.Entity;
import javafx.scene.Group;

import java.util.ArrayList;

public class GameInfo {
    private ArrayList<Entity> entities;
    private Group root;
    private Actor player;
    private double deltaTime;

    public GameInfo(ArrayList<Entity> entities, Group root, Actor player, double deltaTime) {
        this.entities = entities;
        this.root = root;
        this.player = player;
        this.deltaTime = deltaTime;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }

    public Group getRoot() {
        return root;
    }

    public void setRoot(Group root) {
        this.root = root;
    }

    public Actor player() {
        return player;
    }

    public double getDeltaTime() {
        return deltaTime;
    }
    public void setDeltaTime(double deltaTime) {
        this.deltaTime = deltaTime;
    }

}
