import controller.VideoCallFormController;
import dto.Users;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import task.MainThread;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class AppInitializer extends Application {

    private VideoCallFormController controller;
    static Stage stage1 = null;
    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws IOException {
        System.out.println("Socket Starting");
        MainThread mainThread = new MainThread();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/VideoCallForm.fxml"));
        Parent load = loader.load();
        Scene scene = new Scene(load);
        controller = loader.getController();
        mainThread.valueProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue.getMsg().equalsIgnoreCase("start")){
                System.out.println("Started Called in Server Side AppInitializer");
                Stage stage2 = new Stage();
                stage2.setScene(scene);
                stage2.setOnCloseRequest((we)->{

                });
                stage2.show();
                stage1=stage2;
                System.out.println("Showed");
                return;
            }
            if(newValue.getMsg().equalsIgnoreCase("end")){
                System.out.println(stage1);
                if (stage1!=null)
                stage1.close();
            }
            controller.setImageView(newValue.getImg());
            System.out.println("image sent");
        }));
        startScocket(mainThread);
        System.out.println("Started");
    }

    private void startScocket(MainThread ob) throws IOException {
        //Server start = new Server().start();
        Thread main = new Thread(ob);
        main.start();

    }




}
