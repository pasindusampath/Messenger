package controller;

import com.github.sarxos.webcam.Webcam;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import threa.WebCamService;
import util.WebCamView;

import javax.imageio.ImageIO;
import javax.swing.border.Border;
import java.awt.image.BufferedImage;

public class VideoCallFormController {

    public ImageView imageView;
    public BorderPane pane;
    private WebCamService service;
    public void initialize(){
        Webcam cam =Webcam.getWebcams().get(0);
        service= new WebCamService(cam);
        WebCamView view = new WebCamView(service);
        pane.getChildren().add(view.getView());
        service.start();

    }
    public void stopService(){
        service.cancel();
    }


    public void setImageView(BufferedImage bmg){
        imageView.setImage(SwingFXUtils.toFXImage(bmg, null));
    }

    public void startService() {
        service.restart();
    }
}
