package service;

import dto.Message;
import javafx.concurrent.Task;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class CatchingMessageTask extends Task<Message> {
    private Socket socket;
    private DataInputStream inFromServer;

    public CatchingMessageTask(Socket socket) throws IOException {
        this.socket = socket;
        inFromServer = new DataInputStream(socket.getInputStream());
    }

    @Override
    protected Message call() throws Exception {
        while (true) {
            String s = inFromServer.readUTF();
            if(s.equals("img")){
                //Receive name
                String name = inFromServer.readUTF();
                // Receive the image byte array from the server
                //InputStream inputStream = socket.getInputStream();
                System.out.println("Reading: " + System.currentTimeMillis());
                //byte[] sizeAr = new byte[4];

                System.out.println("Reading: 1 ok");
                int size = inFromServer.readInt();
                byte[] imageAr = new byte[size];
                inFromServer.readFully(imageAr);
                System.out.println("Reading: 2 ok");
                BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));
                System.out.println("Converting ok");
                updateValue(new Message(name+" : ",image,null,false,null));
                System.out.println("Updated value");
                //System.out.println("Received " + image.getHeight() + "x" + image.getWidth() + ": " + System.currentTimeMillis());
                ImageIO.write(image, "jpg", new File("test2.jpg"));
                System.out.println("Over");
                continue;
            }
            if(s.equals("names")){
                String names = inFromServer.readUTF();
                String[] split = names.split(":");
                System.out.println(Arrays.toString(split));
                updateValue(new Message("",null,split,false,null));
                continue;
            }
            if(s.equals("<p>")){
                String sender = inFromServer.readUTF();
                String msg = inFromServer.readUTF();
                updateValue(new Message(msg,null,null,true,sender));
                continue;
            }
            if(s.equals("<p><img>")){
                String sender = inFromServer.readUTF();
                int size = inFromServer.readInt();
                byte[] ar = new byte[size];
                inFromServer.readFully(ar);
                BufferedImage read = ImageIO.read(new ByteArrayInputStream(ar));
                updateValue(new Message(sender+" : ",read,null,true,sender));
                continue;
            }


            updateValue(new Message(s,null,null,false,null));
        }
    }
}
