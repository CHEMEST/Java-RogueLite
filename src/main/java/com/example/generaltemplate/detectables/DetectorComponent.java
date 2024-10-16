package com.example.generaltemplate.detectables;

import com.example.generaltemplate.Component;
import com.example.generaltemplate.entities.Entity;

public abstract class DetectorComponent extends Component implements Detectable {
    private final double size;
    public DetectorComponent(Entity entity, double size) {
        super(entity);
        this.size = size;
    }

    public double getSize() {
        return size;
    }
}
