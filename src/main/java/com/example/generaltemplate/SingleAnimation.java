package com.example.generaltemplate;

import com.example.generaltemplate.entities.Entity;

public class SingleAnimation extends Entity{
    private boolean running = false;
    private AnimationPlayer animationPlayer;
    private int fitWidthChange = 0;
    public void setFitWidthChange(int fitWidthChange) {
        this.fitWidthChange = fitWidthChange;
    }
    public SingleAnimation(Vector2D location, AnimationPlayer animationPlayer) {
        super(location, null, "animation");
        this.animationPlayer = animationPlayer;
    }
    public SingleAnimation(AnimationPlayer animationPlayer) {
        super(null, null, "animation");
        this.animationPlayer = animationPlayer;
    }
    public SingleAnimation() {
        super(null, null, "animation");
    }

    public void setAnimationPlayer(AnimationPlayer animationPlayer) {
        this.animationPlayer = animationPlayer;
    }

    public void updateFullScreen(double viewportWidth, double viewportHeight) {
        if (running){
            setLocation(new Vector2D(viewportWidth/2 , viewportHeight/2 ));
//            System.out.println("playing animation");
            animationPlayer.update(fitWidthChange, 0);
        }
        if (animationPlayer.isDone()) running = false;
    }
    public boolean update() {
        if (running){
//            System.out.println("playing animation");
            animationPlayer.update(fitWidthChange, 0);
        }
        if (animationPlayer.isDone()) running = false;
        return false;
    }
    public void setRunning(boolean running){
        this.running = running;
    }
    public void start() {
        this.running = true;
        this.animationPlayer.setFrameIndex(0);
    }

    public boolean isRunning() {
        return running;
    }

    @Override
    public boolean updateLogic(GameInfo gI, Vector2D mouseLocation) {
        return false;
    }
}
