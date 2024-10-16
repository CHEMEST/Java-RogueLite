package com.example.generaltemplate.entities;

import com.example.generaltemplate.GameInfo;
import com.example.generaltemplate.Vector2D;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Objects;

//@huh(huh2 = Tag.ENTITY)
// this.getClass().getAnnotation(huh.class).huh2();
public abstract class Entity {
    private Vector2D location;
    private ImageView img;
    private final String type;
    private double viewportWidth = 1200, viewportHeight = 800;

    public Entity(Vector2D location, ImageView img, String type) {
        this.location = location;
        this.img = img;
        this.type = type;
    }
    public boolean checkCollisionsWithScreenBorder(Entity entity) {
        // left, top, right, bottom
        double x = entity.getLocation().x();
        double y = entity.getLocation().y();
        return x <= 0 || y <= 0 || x >= viewportWidth || y >= viewportHeight;
    }
    public void updateViewport(double viewportWidth,double viewportHeight){
        this.viewportWidth = viewportWidth;
        this.viewportHeight = viewportHeight;
    }

    public void updateVisuals(){
        assert img != null;
//        System.out.println("I have no idea what just happened"); --Roshen Thomas
        img.setX(location.x() - img.getImage().getWidth()/2);
        img.setY(location.y() - img.getImage().getHeight()/2);
        img.setSmooth(false);
    }
    @Override
    public String toString(){
//        String imgURL = this.img.getImage().getUrl();
        return "Entity{" +
                "Type: " + this.type + " | " +
                "Location: " + this.location + " | " +
//                "Image: " + "\"" + imgURL.substring(IMAGE_STARTER.length()) + "\"" +
                "}";

    }
    public ArrayList<Entity> checkCollisions(GameInfo gI, String[] tagsAvoiding){
        ArrayList<Entity> entitiesCollidingWith = new ArrayList<>();
        for (Entity entity : gI.getEntities()){
            boolean avoiding = false;
            for (String tagAvoiding : tagsAvoiding){
                if (Objects.equals(entity.getType(), tagAvoiding)) {
                    avoiding = true;
                    break;
                }
            }
            if ( !avoiding &&
                    entity != this &&
                    entity.getImg().getBoundsInParent()
                            .intersects( getImg().getBoundsInParent() )
            ){
                entitiesCollidingWith.add(entity);
            }
        }
        return entitiesCollidingWith;
    }
    // true = dead / remove-me
    public abstract boolean updateLogic(GameInfo gI, Vector2D mouseLocation);

    public Vector2D getLocation() {
        return location;
    }

    public void setLocation(Vector2D location) {
        this.location = location;
    }
    public void incLocation(Vector2D increment){
        this.setLocation(Vector2D.add(this.location, increment));
    }

    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }

    public String getType() {
        return type;
    }
}
