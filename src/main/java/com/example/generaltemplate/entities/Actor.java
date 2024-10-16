package com.example.generaltemplate.entities;


import com.example.generaltemplate.Component;
import com.example.generaltemplate.Updater;
import com.example.generaltemplate.actionables.Actionable;
import com.example.generaltemplate.detectables.Detectable;
import com.example.generaltemplate.GameInfo;
import com.example.generaltemplate.Vector2D;
import com.example.generaltemplate.moveables.Moveable;
import javafx.scene.image.ImageView;

import static com.example.generaltemplate.PlayerAbility.checkAction;

public class Actor extends KillableEntity {
    private int ticksSinceLastUpdate = 0;
    private int ticksPerUpdate = 0;
    private Moveable moveComponent;
    private Detectable detectComponent;
    // detectComponentAction: for like detecting if something is within a range before starting attacks (movement phases can use this system too)
    private Actionable actionComponent;
    private Updater moveUpdater;
    private Updater detectUpdater;
    private Updater actionUpdater;
    private boolean firstUpdate = true;
    private int updatesAlive = 0;

    public Actor(Vector2D location, ImageView img, double maxHealth, String type) {
        super(location, img, maxHealth, type);
    }

    @Override
    public boolean updateLogic(GameInfo gI, Vector2D mouseLocation){
        if (firstUpdate){

            firstUpdate = false;
        }
        ticksSinceLastUpdate++;
        if (ticksSinceLastUpdate >= ticksPerUpdate){
            if (checkAction((Component) moveComponent, moveUpdater) && updatesAlive > (25)) moveComponent.move(gI, mouseLocation);
            if (checkAction((Component) actionComponent, actionUpdater)) actionComponent.action(gI, mouseLocation);
            ticksSinceLastUpdate = 0;
        }
        updatesAlive++;
        return !isAlive();
    }


    public Actor setMoveComponent(Moveable moveComponent, int updatesPerAction) {
        this.moveComponent = moveComponent;
        moveUpdater = new Updater(updatesPerAction);
        return this;
    }

    public Actor setDetectComponent(Detectable detectComponent, int updatesPerAction) {
        this.detectComponent = detectComponent;

        detectUpdater = new Updater(updatesPerAction);
        return this;
    }

    public Actor setActionComponent(Actionable actionComponent, int updatesPerAction) {
        this.actionComponent = actionComponent;
        actionUpdater = new Updater(updatesPerAction);
        return this;
    }

    public int getTicksSinceLastUpdate() {
        return ticksSinceLastUpdate;
    }

    public int getTicksPerUpdate() {
        return ticksPerUpdate;
    }

    public Moveable getMoveComponent() {
        return moveComponent;
    }

    public Detectable getDetectComponent() {
        return detectComponent;
    }

    public Actionable getActionComponent() {
        return actionComponent;
    }

    public Updater getMoveUpdater() {
        return moveUpdater;
    }

    public Updater getDetectUpdater() {
        return detectUpdater;
    }

    public int getUpdatesAlive() {
        return updatesAlive;
    }

    public void setUpdatesAlive(int updatesAlive) {
        this.updatesAlive = updatesAlive;
    }

    public Updater getActionUpdater() {
        return actionUpdater;
    }

    public void setTicksSinceLastUpdate(int ticksSinceLastUpdate) {
        this.ticksSinceLastUpdate = ticksSinceLastUpdate;
    }

    public void setTicksPerUpdate(int ticksPerUpdate) {
        this.ticksPerUpdate = ticksPerUpdate;
    }
}

