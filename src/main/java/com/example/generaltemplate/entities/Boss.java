package com.example.generaltemplate.entities;

import com.example.generaltemplate.*;
import javafx.scene.image.ImageView;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

import static com.example.generaltemplate.PlayerAbility.checkAction;

public class Boss extends Enemy{
    private final ArrayList<AbilityInfo> abilities = new ArrayList<>();
    private AbilityInfo specialAbility;
    private AbilityInfo activeAbility;
    public Boss(Vector2D location, ImageView img, double maxHealth, String type, double expDroppedOnKill, ArrayList<AbilityInfo> oldAbilities, AbilityInfo specialAbility, int value) {
        super(location, img, maxHealth, type, expDroppedOnKill, value);
        this.abilities.addAll(oldAbilities);
        for (AbilityInfo ability : this.abilities){
//            try {
//
////                Constructor<AbilityInfo> constructor = (Constructor<AbilityInfo>) ability.getClass().getConstructor(int.class, String.class);
////                AbilityInfo instance = constructor.newInstance(7, "temp");
//
//            } catch (Exception _){}
//
            if (ability.getActionComponent() instanceof Component){
                ((Component) ability.getActionComponent()).setEntity(this);
            }
            if (ability.getMoveComponent() instanceof Component){
                ((Component) ability.getMoveComponent()).setEntity(this);
            }
        }
        abilities.add(specialAbility);
        this.specialAbility = specialAbility;
        this.activeAbility = specialAbility;
    }

    private AbilityInfo nextAbility(AbilityInfo currentAbility) {
        int index = abilities.indexOf(currentAbility);
        if (index < abilities.size() - 1){
            return abilities.get(index + 1);
        } else {
            return abilities.get(0);
        }
    }

    public AbilityInfo getSpecialAbility() {
        return specialAbility;
    }

    public void setSpecialAbility(AbilityInfo specialAbility) {
        this.specialAbility = specialAbility;
    }

    @Override
    public boolean updateLogic(GameInfo gI, Vector2D mouseLocation){

        setTicksSinceLastUpdate(getTicksSinceLastUpdate() + 1);
        if (getTicksSinceLastUpdate() >= getTicksPerUpdate()){
            if (checkAction((Component) activeAbility.getMoveComponent(), activeAbility.getMoveUpdater()) && getUpdatesAlive() > (50)) activeAbility.getMoveComponent().move(gI, mouseLocation);
            if (checkAction((Component) activeAbility.getActionComponent(), activeAbility.getActionUpdater())) activeAbility.getActionComponent().action(gI, mouseLocation);
            setTicksSinceLastUpdate(0);
        }
        if (Math.random() > .9){
//            System.out.println("switched abilities");
//            System.out.println(abilities);
//            System.out.println(activeAbility);
            this.activeAbility = nextAbility(this.activeAbility);
        }
        setUpdatesAlive(getUpdatesAlive() + 1);
        return !isAlive();
    }
}
