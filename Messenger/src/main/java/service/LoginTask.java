package service;

import dto.Message;
import javafx.concurrent.Task;

import java.io.DataInputStream;
import java.net.Socket;

public class LoginTask extends Task<Message> {
    Socket socket ;

    public LoginTask(Socket socket) {
        this.socket = socket;
    }

    @Override
    protected Message call() throws Exception {
        DataInputStream in = new DataInputStream(socket.getInputStream());
        while (true){
            String s = in.readUTF();
            if(s.equals("111"))break;
            else updateValue(new Message("no",null,null,false,""));
        }
        updateValue(new Message("ok",null,null,false,""));
        return null;
    }
}
