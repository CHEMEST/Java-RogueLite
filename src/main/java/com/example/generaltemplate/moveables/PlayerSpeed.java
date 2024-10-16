package com.example.generaltemplate.moveables;

public enum PlayerSpeed {
    VERY_SLOW(25.0),
    SLOW(150.0),
    MEDIUM(350.0),
    FAST(550.0),
    VERY_FAST(900.5);

    private final double value;
    PlayerSpeed(double value) {
        this.value = value;
    }
    public int getUpdates(){
        return 0;
    }

    public double value() {
        return value;
    }
}
