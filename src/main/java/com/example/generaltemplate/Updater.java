package com.example.generaltemplate;

public class Updater {
    private int updatesSinceLastAction;
    private int updatesPerAction;
    public Updater(int updatesPerAction) {
        this.updatesSinceLastAction = updatesPerAction;
        this.updatesPerAction = updatesPerAction;
    }
    public boolean tick(){
        if (updatesSinceLastAction < updatesPerAction || (Math.random() > 0.5)){
            updatesSinceLastAction++;
            return false;
        } else{
            updatesSinceLastAction = 0;
//            System.out.println("Updater completed");
            return true;
        }
    }
    public int getUpdatesSinceLastAction() {
        return updatesSinceLastAction;
    }

    public void setUpdatesSinceLastAction(int updatesSinceLastAction) {
        this.updatesSinceLastAction = updatesSinceLastAction;
    }

    public int getUpdatesPerAction() {
        return updatesPerAction;
    }

    public void setUpdatesPerAction(int updatesPerAction) {
        this.updatesPerAction = updatesPerAction;
    }
}
