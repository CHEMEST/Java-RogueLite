package com.example.generaltemplate.moveables;

public enum BulletSpeed {
    SLOW(450.0),
    MEDIUM(650),
    FAST(1000);

    private final double value;

    BulletSpeed(double value) {
        this.value = value;
    }

    public double value() {
        return value;
    }
}
