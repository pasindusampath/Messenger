package connection;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerConnection {
    private static ServerConnection serverConnection;
    private ServerSocket  server;

    private ServerConnection() throws IOException {
        server = new ServerSocket(9001);
    }


    public static ServerConnection getServerConnection() throws IOException {
        return serverConnection == null ? serverConnection=new ServerConnection() : serverConnection;
    }

    public ServerSocket getServer(){
        return server;
    }


}
