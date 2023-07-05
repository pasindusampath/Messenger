package controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.awt.image.BufferedImage;

public class VideoCallFormController {

    public ImageView imageView;

    public void setImageView(BufferedImage bmg){
        if(bmg!=null)
        imageView.setImage(SwingFXUtils.toFXImage(bmg, null));
    }
}
