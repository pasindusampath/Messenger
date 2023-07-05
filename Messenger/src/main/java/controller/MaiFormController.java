package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import connection.SocketConnection;
import dto.Message;
import dto.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import service.CatchingMessageTask;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;


public class MaiFormController {
    public JFXTextField txtMessage;
    public VBox vBox;
    public JFXButton btnSendMessage;
    public JFXTextField txtName;
    public JFXButton btnChooseImage;
    public VBox availableMembers;
    public HashMap<String, User> hm = new HashMap<>();
    public VideoCallFormController vcall;
    Thread t;

    public void initialize() {
        txtName.setEditable(false);
        txtMessage.setEditable(true);
        try {
            Socket socket = SocketConnection.getConnection().getSocket();
            CatchingMessageTask task = new CatchingMessageTask(socket);
            task.valueProperty().addListener(new ChangeListener<Message>() {
                @Override
                public void changed(ObservableValue<? extends Message> observable, Message oldValue, Message newValue) {
                    //System.out.println(newValue);
                    if (newValue.getNames() != null) {
                        setAvailableMembers(newValue.getNames());
                        return;
                    }
                    if (newValue.isPrivate()) {
                        PrivateMessageFormController controller = hm.get(newValue.getSender()).getController();
                        controller.received();
                        if (newValue.getImage() == null) {
                            controller.receiveMessage(newValue);
                        } else {
                            controller.receiveImage(newValue);
                        }
                        return;
                    }
                    Label l1 = new Label(newValue.getMessage());
                    l1.setWrapText(true);
                    l1.setMaxWidth(100);
                    l1.setMinHeight(20);
                    if (newValue.getImage() == null) {
                        HBox hb = new HBox();
                        hb.setAlignment(Pos.CENTER_LEFT);
                        hb.getChildren().add(l1);
                        vBox.getChildren().add(hb);
                    } else {
                        HBox hb = new HBox();
                        hb.setAlignment(Pos.CENTER_LEFT);
                        hb.getChildren().add(l1);
                        ImageView imageView = new ImageView(SwingFXUtils.toFXImage(newValue.getImage(), null));
                        imageView.setFitHeight(100);
                        imageView.setFitWidth(100);
                        hb.getChildren().add(imageView);
                        vBox.getChildren().add(hb);
                    }
                }
            });
            t = new Thread(task);
            t.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAvailableMembers(String[] names) {
        availableMembers.getChildren().clear();
        for (String name : names) {
            if(name.equals(""))continue;
            Button priv = new Button("PRIVATE");
            if (hm.get(name) == null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PrivateMsgForm.fxml"));
                try {
                    Parent load = loader.load();
                    ;
                    hm.put(name, new User(name, new Scene(load), loader.getController()));
                    PrivateMessageFormController controller = loader.getController();
                    controller.txtFriendName.setText(name);
                    controller.txtUserName.setText(txtName.getText());

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            Label label = new Label(name);
            label.setAlignment(Pos.CENTER);
            label.getStyleClass().add("userLabel");
            label.setMaxWidth(89);
            label.setMaxHeight(45);
            //Button priv = new Button("PRIVATE");
            priv.getStyleClass().add("userButton");
            priv.setMaxWidth(78);
            PrivateMessageFormController controller = hm.get(name).getController();
            controller.setButton(priv);
            controller.animateButton();
            priv.setOnAction((ae) -> {
                Scene parent = hm.get(name).getScene();
                Stage stage = new Stage();
                stage.setScene(parent);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(txtName.getScene().getWindow());
                stage.setOnCloseRequest((we) -> {
                    controller.viewed();
                });
                stage.show();
            });
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER);
            hBox.getChildren().add(label);
            hBox.getChildren().add(priv);
            availableMembers.getChildren().add(hBox);
        }
    }

    public void sendMessageOnKeyAction(ActionEvent actionEvent) throws IOException {
        Label l1 = new Label("ME : " + txtMessage.getText());
        l1.setWrapText(true);
        l1.setMaxWidth(100);
        l1.setMinHeight(20);
        HBox hb = new HBox();
        hb.setAlignment(Pos.CENTER_RIGHT);
        hb.getChildren().add(l1);
        vBox.getChildren().add(hb);
        SocketConnection.getConnection().sendToServer(txtName.getText() + " : " + txtMessage.getText());
    }

    public void sendMessageOnAction(ActionEvent actionEvent) throws IOException {
        sendMessageOnKeyAction(actionEvent);
    }

    public void btnChoseImgOnAction(ActionEvent actionEvent) {
        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog((Stage) (btnChooseImage.getScene().getWindow()));
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
                SocketConnection.getConnection().sendToServer(file, txtName.getText());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
