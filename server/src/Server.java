import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class Server {
    public static void main(String[] args) throws IOException {

        ServerSocket server = new ServerSocket(9001);
        ArrayList<Users> sockets = new ArrayList<>();
        while (true) {
            Socket accept = server.accept();
            DataInputStream inFormClient = new DataInputStream(accept.getInputStream());
            Thread t1 = new Thread() {

                public void run() {
                    Users usr =null;
                    try {
                        String name = "";
                        while(true){
                            name = inFormClient.readUTF();
                            if(isAvailable(name,sockets)){
                                sendPrivate(accept,"111");
                                break;
                            }else {
                                sendPrivate(accept,"000");
                            }
                            
                        }

                        System.out.println(name+": Joined To The Server");
                         usr= new Users(name, accept);
                        sockets.add(usr);
                        sendAllUsers(sockets);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    while (true) {
                        try {
                            String s = inFormClient.readUTF();
                            if (s.equals("img")) {
                                sendAll(sockets, accept, s);
                                String name = inFormClient.readUTF();
                                System.out.println(name + ": trying to send image");
                                /*byte[] sizeAr = new byte[4];
                                inFormClient.read(sizeAr)*/;
                                int size = inFormClient.readInt();

                                byte[] imageAr = new byte[size];
                                inFormClient.readFully(imageAr);
                                System.out.println(name + ": Got Image by server");
                                BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));
                                sendAll(sockets, accept, name);
                                sendAll(sockets, accept, imageAr);
                                ImageIO.write(image, "jpg", new File("test.jpg"));
                                //sendAll(sockets,accept,imageAr);
                                continue;
                            }
                            if(s.equals("<p>")){
                                String sender = inFormClient.readUTF();
                                String receiver = inFormClient.readUTF();
                                String msg = inFormClient.readUTF();
                                sendPrivateMsg(sockets,sender , receiver,msg);
                                continue;
                            }
                            if(s.equals("<p><img>")){
                                String sender = inFormClient.readUTF();
                                String receiver = inFormClient.readUTF();
                                int size = inFormClient.readInt();
                                byte[] ar = new byte[size];
                                inFormClient.readFully(ar);
                                sendImagePrivate(sockets,sender,receiver,ar);
                            }
                            try {
                                sendAll(sockets, accept, s);
                            } catch (Exception e) {

                            }
                            System.out.println(s);
                        } catch (IOException e) {
                            e.printStackTrace();
                            sockets.remove(usr);
                            break;
                        }
                    }
                }
            };

            t1.start();
        }
    }

    private static void sendPrivate( Socket user, String msg) throws IOException {
        DataOutputStream out = new DataOutputStream(user.getOutputStream());
        out.writeUTF(msg);
        out.flush();

    }

    private static boolean isAvailable(String name, ArrayList<Users> sockets) {
        for(Users usr : sockets){
            if(usr.getName().equals(name)){
                return false;
            }
        }
        return true;
    }

    private static void sendImagePrivate(ArrayList<Users> users,String sender, String receiver, byte[] ar) throws IOException {
        for(Users user : users){
            if(user.getName().equals(receiver)){
                DataOutputStream outToClient = new DataOutputStream(user.getSocket().getOutputStream());
                outToClient.writeUTF("<p><img>");
                outToClient.writeUTF(sender);
                outToClient.writeInt(ar.length);
                outToClient.write(ar);
            }
        }
    }

    private static void sendPrivateMsg(ArrayList<Users> users,String sender , String receiver, String msg) throws IOException {
        for(Users user : users){
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

    private static void sendAllUsers(ArrayList<Users> users) {
        for (Users user:users) {
            String names = "";
            for(Users user1 : users){
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

    public static void sendAll(ArrayList<Users> clients, Socket socket, String message) throws IOException {
        for (Users client : clients) {
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

    public static void sendAll(ArrayList<Users> clients, Socket socket, byte [] imagear) throws IOException {
        for (Users client : clients) {
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

    public void start(){
    }

}
