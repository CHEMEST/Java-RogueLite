package com.example.generaltemplate;

import com.example.generaltemplate.actionables.Actionable;
import com.example.generaltemplate.moveables.Moveable;
import javafx.animation.AnimationTimer;

public class PlayerAbility {
    private final Actionable actionable;
    private final Moveable moveable;
    private final String name;
    private double coolDownSeconds;
    private double coolDownSecondsLeft;
    private boolean onCoolDown;
    AnimationTimer timer;

    public PlayerAbility(Moveable moveable, String name, double coolDownSeconds, double coolDownSecondsLeft) {
        this.name = name;
        this.actionable = null;
        this.moveable = moveable;
        this.coolDownSeconds = coolDownSeconds;
        this.coolDownSecondsLeft = coolDownSecondsLeft;
        this.onCoolDown = coolDownSecondsLeft > 0;
        startTimer();
    }
    public PlayerAbility(Actionable actionable, String name, double coolDownSeconds, double coolDownSecondsLeft) {
        this.actionable = actionable;
        this.name = name;
        this.moveable = null;
        this.coolDownSeconds = coolDownSeconds;
        this.coolDownSecondsLeft = coolDownSecondsLeft;
        this.onCoolDown = coolDownSecondsLeft > 0;
        startTimer();
    }
    public void startTimer(){
        timer = new AnimationTimer() {
            long t = System.nanoTime();
            @Override
            public void handle(long now) {
                if (now - t > 1000000000){
                    t = now;
                    if (onCoolDown) coolDownSecondsLeft--;
                    if (coolDownSecondsLeft <= 0){
                        onCoolDown = false;
                    }

                }
            }
        };
        timer.start();
    }

    public boolean use(GameInfo gI, Vector2D mouseLocation){
//        System.out.println(onCoolDown);
        if (!onCoolDown){
            assert actionable != null || moveable != null;
            Object component = actionable != null ? actionable : moveable;

            if (component instanceof Actionable){
                ((Actionable) component).action(gI, mouseLocation);
            } else {
//                ((Moveable) component).move(gI, mouseLocation);
            }

            onCoolDown = true;
        }
        return false;
    }
    public static boolean checkAction(Component component, Updater updater) {
        return component != null && (updater.getUpdatesPerAction() == 0 || updater.tick());
    }

    public double getCoolDownSecondsLeft() {
        return coolDownSecondsLeft;
    }

    public boolean isOnCoolDown() {
        return onCoolDown;
    }

    public double getCoolDownSeconds() {
        return coolDownSeconds;
    }

    public void setCoolDownSeconds(double coolDownSeconds) {
        this.coolDownSeconds = coolDownSeconds;
    }

    public String getName() {
        return name;
    }

    public Component getAbilityComponent() {
        return actionable != null ? (Component) actionable : (Component) moveable;
    }
}
