package com.example.generaltemplate;

import com.example.generaltemplate.entities.Entity;

public abstract class Component {
    private Entity entity;

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public Component(Entity entity) {
        this.entity = entity;
    }
}
