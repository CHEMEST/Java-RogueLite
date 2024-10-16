package com.example.generaltemplate;

import com.example.generaltemplate.actionables.Actionable;
import com.example.generaltemplate.moveables.Moveable;

public class AbilityInfo {
    private Moveable moveComponent;
    private Actionable actionComponent;
    private Updater moveUpdater;
    private Updater actionUpdater;

    public AbilityInfo(Moveable moveComponent, Actionable actionComponent, Updater moveUpdater, Updater actionUpdater) {
        this.moveComponent = moveComponent;
        this.actionComponent = actionComponent;
        this.moveUpdater = moveUpdater;
        this.actionUpdater = actionUpdater;
    }

    public Moveable getMoveComponent() {
        return moveComponent;
    }

    public AbilityInfo setMoveComponent(Moveable moveComponent) {
        this.moveComponent = moveComponent;
        return this;
    }

    public Actionable getActionComponent() {
        return actionComponent;
    }

    public AbilityInfo setActionComponent(Actionable actionComponent) {
        this.actionComponent = actionComponent;
        return this;
    }

    public Updater getMoveUpdater() {
        return moveUpdater;
    }

    public AbilityInfo setMoveUpdater(Updater moveUpdater) {
        this.moveUpdater = moveUpdater;
        return this;
    }

    public Updater getActionUpdater() {
        return actionUpdater;
    }

    public AbilityInfo setActionUpdater(Updater actionUpdater) {
        this.actionUpdater = actionUpdater;
        return this;
    }
    public String toString(){
        return "Ability{ Action: " + actionComponent.getClass().getSimpleName() + "}";
    }
}
