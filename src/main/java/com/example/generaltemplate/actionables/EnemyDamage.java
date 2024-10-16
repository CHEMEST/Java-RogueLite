package com.example.generaltemplate.actionables;

public enum EnemyDamage {
    LOW(5.0),
    MEDIUM(10.0),
    HIGH(20.0);

    private final double value;

    EnemyDamage(double value) {
        this.value = value;
    }

    public double value() {
        return value;
    }
}

