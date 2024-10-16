package com.example.generaltemplate.moveables;

public enum EnemySpeed {
    VERY_SLOW(75),
    SLOW(200),
    MEDIUM(275),
    FAST(375),
    VERY_FAST(500);

    private final double value;
    EnemySpeed(double value) {
        this.value = value;
    }
    public double value() {
        return value;
    }
}
