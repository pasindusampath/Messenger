package threa;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import connection.SocketConnection;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

//Credit
//https://gist.github.com/james-d/f826c9f38d53628114124a56fb7c4557#file-webcamservice-java
public class WebCamService extends Service<Image> {
    private BufferedImage bimg;

    private final Webcam cam ;

    private final WebcamResolution resolution ;

    public WebCamService(Webcam cam, WebcamResolution resolution) {
        this.cam = cam ;
        this.resolution = resolution;
        cam.setCustomViewSizes(new Dimension[] {resolution.getSize()});
        cam.setViewSize(resolution.getSize());
    }

    public WebCamService(Webcam cam) {
        this(cam, WebcamResolution.QVGA);
    }

    @Override
    public Task<Image> createTask() {

        return new Task<Image>() {
            @Override
            protected Image call() throws Exception {


                Thread t1 = new Thread(){
                    @Override
                    public void run(){
                        int count = 0;
                        while (!isCancelled()){
                            try {
                                Thread.sleep(1000);
                                updateProgress(50,100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            /*if(bimg!=null) {
                                //String s = QRDecoder.decodeQRCode(bimg);
                                if(s!=null){
                                    updateMessage(s);
                                }else{
                                    try {
                                        updateProgress(20,100);
                                        Thread.sleep(200);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    continue;
                                }
                            }*/
                            count++;

                            if(bimg!=null&&count%3!=0){
                                try {
                                    SocketConnection.getConnection().videoCall("running",bimg);
                                    System.out.println("running");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            try {
                                updateProgress(100,100);
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                };
                t1.start();
                try {
                    cam.open();
                    while (!isCancelled()) {
                        if (cam.isImageNew()) {
                            bimg = cam.getImage();
                            updateValue(SwingFXUtils.toFXImage(bimg, null));
                        }
                    }
                    System.out.println("Cancelled, closing cam");
                    cam.close();
                    System.out.println("Cam closed");
                    return getValue();
                } finally {
                    cam.close();
                }

            }

        };
    }


    public int getCamWidth() {
        return resolution.getSize().width ;
    }

    public int getCamHeight() {
        return resolution.getSize().height ;
    }

}
