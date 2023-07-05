import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static void main(String[] args) throws IOException {
        AppInitializer.main(args);
    }

    /*private static void sendPrivate( Socket user, String msg) throws IOException {
        DataOutputStream out = new DataOutputStream(user.getOutputStream());
        out.writeUTF(msg);
        out.flush();

    }

    private static boolean isAvailable(String name, ArrayList<dto.Users> sockets) {
        for(dto.Users usr : sockets){
            if(usr.getName().equals(name)){
                return false;
            }
        }
        return true;
    }

    private static void sendImagePrivate(ArrayList<dto.Users> users,String sender, String receiver, byte[] ar) throws IOException {
        for(dto.Users user : users){
            if(user.getName().equals(receiver)){
                DataOutputStream outToClient = new DataOutputStream(user.getSocket().getOutputStream());
                outToClient.writeUTF("<p><img>");
                outToClient.writeUTF(sender);
                outToClient.writeInt(ar.length);
                outToClient.write(ar);
            }
        }
    }

    private static void sendPrivateMsg(ArrayList<dto.Users> users,String sender , String receiver, String msg) throws IOException {
        for(dto.Users user : users){
            if(user.getName().equals(receiver)){
                DataOutputStream outToClient = new DataOutputStream(user.getSocket().getOutputStream());
                outToClient.writeUTF("<p>");
                outToClient.flush();
                outToClient.writeUTF(sender);
                outToClient.flush();
                outToClient.writeUTF(msg);
                outToClient.flush();
            }
        }
    }

    private static void sendAllUsers(ArrayList<dto.Users> users) {
        for (dto.Users user:users) {
            String names = "";
            for(dto.Users user1 : users){
                if(user.getSocket().getPort()!=user1.getSocket().getPort()){
                    names=names+user1.getName()+":";
                }
            }

            DataOutputStream outFromClient = null;
            try {
                outFromClient = new DataOutputStream(user.getSocket().getOutputStream());
                outFromClient.writeUTF("names");
                outFromClient.writeUTF(names);
                outFromClient.flush();
            } catch (IOException e) {
                users.remove(user);
                e.printStackTrace();
            }

        }
    }

    public static void sendAll(ArrayList<dto.Users> clients, Socket socket, String message) throws IOException {
        for (dto.Users client : clients) {
            try {
                DataOutputStream outFromClient = new DataOutputStream(client.getSocket().getOutputStream());
                if (client.getSocket().getPort() != socket.getPort()) {
                    outFromClient.writeUTF(message);
                    outFromClient.flush();
                }
            } catch (Exception e) {
                clients.remove(client);
            }


        }
    }

    public static void sendAll(ArrayList<dto.Users> clients, Socket socket, byte [] imagear) throws IOException {
        for (dto.Users client : clients) {
            DataOutputStream outputStream = new DataOutputStream(client.getSocket().getOutputStream());
            if (client.getSocket().getPort() != socket.getPort()) {
                try {
                    int size = imagear.length;
                    outputStream.writeInt(size);
                    outputStream.write(imagear);
                    outputStream.flush();
                    System.out.println("Flushed: " + System.currentTimeMillis());
                } catch (IOException e) {
                    clients.remove(client);
                }

            }

        }
    }
*/


}
