package com.example.generaltemplate;


public enum Tag {
    ENTITY(new Tag[]{}),
    PLAYER(new Tag[]{Tag.ENTITY}),
    ENEMY(new Tag[]{Tag.ENTITY}),
    COLLECTIBLE(new Tag[]{Tag.ENTITY}),
    BOSS(new Tag[]{Tag.ENEMY, Tag.ENTITY}),
    ATTACK(new Tag[]{Tag.ENTITY});



    private final Tag[] parents;
    Tag(Tag[] parents){
        this.parents = parents;
    }

    public boolean isOf(Tag parent){
        return containsT(this.parents, parent);
    }

    private static boolean containsT(Tag[] array, Tag targetTag) {
        for (Tag element : array) {
            if (element == targetTag) {
                return true;
            }
        }
        return false;
    }

}
