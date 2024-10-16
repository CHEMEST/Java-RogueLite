package com.example.generaltemplate;

import com.example.generaltemplate.entities.Entity;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Scale;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class AnimationPlayer {
    private final String directoryPath;
    private final String fileType;
    private final String frameName;
    private final Entity entity;
    private final int totalFrames;
    private final int updatesPerFrame;
    private int updatesSinceLastFrame = 0;
    private int frameIndex = 1;
//    private final ArrayList<Image> images = new ArrayList<>();


    public int getTotalFrames() {
        return totalFrames;
    }

    public int getFrameIndex() {
        return frameIndex;
    }
    public boolean isDone(){
        return getFrameIndex() > getTotalFrames();
    }

    public AnimationPlayer(Entity entity, String directoryPath, String frameName, String fileType, int totalFrames, int updatesPerFrame) {
        this.entity = entity;
        this.directoryPath = directoryPath;
        this.fileType = fileType;
        this.frameName = frameName;
        this.totalFrames = totalFrames;
        this.updatesPerFrame = updatesPerFrame;

//        for (String s : Objects.requireNonNull(new File(directoryPath).list())){
////            System.out.println(s);
//            try{
//                images.add(new Image(new FileInputStream(directoryPath + "/" + s)));
//            } catch (Exception e){
//                System.out.println(s);
//            }
//
//        }
        try {
            String path = directoryPath + "/" + frameName + 1 + fileType;
            entity.setImg(new ImageView(new Image(new FileInputStream(path))));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void update(double fitWidthChange, double x) {

        if (updatesSinceLastFrame >= updatesPerFrame) {
            if (frameIndex <= totalFrames) {
                String path = directoryPath + "/" + frameName + frameIndex + fileType;
                try {
                    entity.setImg(new ImageView(new Image(new FileInputStream(path))));
                    entity.getImg().setPreserveRatio(true);
                    entity.getImg().setFitWidth(entity.getImg().getImage().getWidth() + fitWidthChange);
                    // entity.getImg().getTransforms().add(new Scale(-1, 1));
                } catch (FileNotFoundException ignored) {}
                frameIndex++;
            } else {
                frameIndex = 1;
            }
            updatesSinceLastFrame = 0;
        } else {
            updatesSinceLastFrame++;
        }
    }

    public void setFrameIndex(int i) {
        this.frameIndex = i;
    }
}
