package connection;

import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.file.Files;

public class SocketConnection {
    private  Socket socket;
    private static SocketConnection co;
    DataOutputStream dataOutputStream;
    private SocketConnection() throws IOException {
       socket = new Socket("localhost",9001);
       dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }

    public static SocketConnection getConnection() throws IOException {
        return co==null ? co=new SocketConnection() : co;
    }

    public Socket getSocket(){
        System.out.println("Reached");
        return socket;
    }

    public void sendToServer(String msg) throws IOException {
        dataOutputStream.writeUTF(msg);
        dataOutputStream.flush();
    }

    public void sendToServer(File imageFile,String name) throws IOException {
        sendToServer("img");
        sendToServer(name);
        //File imageFile = new File(img); // Provide the path to your image file
        BufferedImage image = ImageIO.read(imageFile);
        byte[] bytes = Files.readAllBytes(imageFile.toPath());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", byteArrayOutputStream);
        System.out.println(bytes.length);
        //byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
        dataOutputStream.writeInt(bytes.length);
        dataOutputStream.write(bytes);
        dataOutputStream.flush();
        System.out.println("Flushed: " + System.currentTimeMillis());

    }

    public void sendPrivateImage(File image, String sender, String receiver) throws IOException {
        sendToServer("<p><img>");
        sendToServer(sender);
        sendToServer(receiver);
        BufferedImage img = ImageIO.read(image);
        byte[] bytes = Files.readAllBytes(image.toPath());
        dataOutputStream.writeInt(bytes.length);
        dataOutputStream.write(bytes);

    }

    public void videoCall(String signal,BufferedImage img) throws IOException {
        dataOutputStream.writeUTF(signal);//signal
        dataOutputStream.flush();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(img, "jpg", baos);
        byte[] bytes = baos.toByteArray();
        dataOutputStream.writeInt(bytes.length);
        dataOutputStream.flush();
        dataOutputStream.write(bytes);
        dataOutputStream.flush();
    }

}
