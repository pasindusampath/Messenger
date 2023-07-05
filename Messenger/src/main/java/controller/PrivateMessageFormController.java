package controller;

import com.jfoenix.controls.JFXTextField;
import connection.SocketConnection;
import dto.Message;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.naming.ldap.PagedResultsControl;
import java.io.File;
import java.io.IOException;

public class PrivateMessageFormController {
    private Button button;
    private boolean isAllRead = true;
    public JFXTextField txtFriendName;
    public JFXTextField txtUserName;
    public VBox vBox;
    public JFXTextField txtMessage;
    private VideoCallFormController controller;
    private Stage stage;

    public void initialize() {
        txtFriendName.setEditable(false);
        txtUserName.setEditable(false);
        txtMessage.setEditable(true);
    }

    public void sendMessageOnKeyAction(ActionEvent actionEvent) {
        sendMessageOnAction(actionEvent);
    }

    public void sendMessageOnAction(ActionEvent actionEvent) {
        Label l1 = new Label("ME : " + txtMessage.getText());
        l1.setWrapText(true);
        l1.setMaxWidth(100);
        l1.setMinHeight(20);
        HBox hb = new HBox();
        hb.setAlignment(Pos.CENTER_RIGHT);
        hb.getChildren().add(l1);
        vBox.getChildren().add(hb);
        try {
            SocketConnection.getConnection().sendToServer("<p>");
            SocketConnection.getConnection().sendToServer(txtUserName.getText());//Sender
            SocketConnection.getConnection().sendToServer(txtFriendName.getText());//Receiver
            SocketConnection.getConnection().sendToServer(txtUserName.getText() + " : " + txtMessage.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnChoseImgOnAction(ActionEvent actionEvent) {
        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog((Stage) (vBox.getScene().getWindow()));
        if (file != null) {
            Image image1 = new Image(file.toURI().toString());
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);
            ImageView imageView = new ImageView(image1);
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);
            hBox.getChildren().add(new Label("Me : "));
            hBox.getChildren().add(imageView);
            vBox.getChildren().add(hBox);
            try {
                SocketConnection.getConnection().sendPrivateImage(file, txtUserName.getText() , txtFriendName.getText());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void receiveMessage(Message newValue) {
        Label l1 = new Label(newValue.getMessage());
        l1.setWrapText(true);
        l1.setMaxWidth(100);
        l1.setMinHeight(20);
        HBox hb = new HBox();
        hb.setAlignment(Pos.CENTER_LEFT);
        hb.getChildren().add(l1);
        vBox.getChildren().add(hb);
    }

    public void receiveImage(Message newValue) {
        Label l1 = new Label(newValue.getMessage());
        l1.setWrapText(true);
        l1.setMaxWidth(100);
        l1.setMinHeight(20);
        HBox hb = new HBox();
        hb.setAlignment(Pos.CENTER_LEFT);
        hb.getChildren().add(l1);
        ImageView imageView = new ImageView(SwingFXUtils.toFXImage(newValue.getImage(), null));
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        hb.getChildren().add(imageView);
        vBox.getChildren().add(hb);
    }

    public void setButton(Button button){
        this.button=button;
        setAnimation();
    }
    Timeline timeline;
    private void setAnimation(){
        if (timeline!=null){
            timeline.stop();
        }
         timeline= new Timeline(
                new KeyFrame(Duration.ZERO, (ae)->{
                    button.getStyleClass().add("animated-button");
                }),
                new KeyFrame(Duration.seconds(1), (ae)->{
                    button.getStyleClass().remove("animated-button");
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.pause();
    }

    public void received(){
        isAllRead=false;
        timeline.play();
    }
    public void viewed(){
        isAllRead=true;
        timeline.pause();
        button.getStyleClass().removeAll("userButton","animated-button");
        button.getStyleClass().add("userButton");
    }

    public void animateButton(){
        if(!isAllRead){
            System.out.println("Animation Started");
            received();
        }
    }



    public void btnVideoCallOnAction(ActionEvent actionEvent) throws IOException {
        SocketConnection.getConnection().sendToServer("<p><vid>");
        if (controller==null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/VideoCallForm.fxml"));
            Parent load = loader.load();
            controller = loader.getController();
            stage = new Stage();
            stage.setScene(new Scene(load));
            stage.setOnCloseRequest((we)->{
                try {
                    SocketConnection.getConnection().sendToServer("end");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                controller.stopService();
            });
            stage.show();
        }else {
            stage.show();
            controller.startService();
        }

    }
    public VideoCallFormController getVideoCallController(){
        return controller;
    }

}
