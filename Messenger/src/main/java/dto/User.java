package dto;

import controller.PrivateMessageFormController;
import javafx.scene.Scene;

public class User {
    private String name;
    private Scene scene;
    private PrivateMessageFormController controller;

    public User(String name, Scene scene, PrivateMessageFormController controller) {
        this.name = name;
        this.scene = scene;
        this.controller = controller;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public PrivateMessageFormController getController() {
        return controller;
    }

    public void setController(PrivateMessageFormController controller) {
        this.controller = controller;
    }
}
