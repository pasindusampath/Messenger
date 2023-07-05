package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import connection.SocketConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.Window;
import service.LoginTask;

import java.io.IOException;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginFormController {
    public JFXTextField txtUserName;
    public JFXButton btnLogin;

    public void initialize() throws IOException {
        Socket socket = SocketConnection.getConnection().getSocket();
        LoginTask task = new LoginTask(socket);
        Thread t1 = new Thread(task);
        task.valueProperty().addListener((list,ov,nv)->{
            if(nv==null)return;
            if(nv.getMessage().equals("ok")){
                if(t1.isAlive()){
                    t1.interrupt();
                }
                Stage window = (Stage)txtUserName.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainForm.fxml"));
                Parent load = null;
                try {
                    load = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                MaiFormController controller = loader.getController();
                controller.txtName.setText(txtUserName.getText());
                window.setScene(new Scene(load));
                window.setResizable(false);
                window.setOnCloseRequest((we)->{
                    try {
                        SocketConnection.getConnection().getSocket().close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }else {
                new Alert(Alert.AlertType.ERROR,"Name Already Exist Try Another Name").show();
            }
        });
        t1.start();
    }

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        String text = txtUserName.getText();
        Pattern compile = Pattern.compile("^[A-z|\\s]{3,}$");
        Matcher matcher = compile.matcher(text);
        if(matcher.matches()){
            SocketConnection.getConnection().sendToServer(txtUserName.getText());
        }else {
            new Alert(Alert.AlertType.ERROR,"Input Valid Name :(").show();
        }
    }
}
