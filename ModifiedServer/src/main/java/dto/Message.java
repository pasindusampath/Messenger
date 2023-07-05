package dto;

import java.awt.image.BufferedImage;

public class Message {
    private String msg;
    private BufferedImage img;

    public Message(String msg, BufferedImage img) {
        this.msg = msg;
        this.img = img;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public BufferedImage getImg() {
        return img;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }
}
