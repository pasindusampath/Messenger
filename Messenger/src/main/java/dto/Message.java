package dto;

import java.awt.image.BufferedImage;

public class Message {
    private String message;
    private BufferedImage image;
    private String[] names;
    private boolean isPrivate;
    private String sender;

    public Message(String message, BufferedImage image, String[] names, boolean isPrivate, String sender) {
        this.message = message;
        this.image = image;
        this.names = names;
        this.isPrivate = isPrivate;
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public String[] getNames() {
        return names;
    }

    public void setNames(String[] names) {
        this.names = names;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
