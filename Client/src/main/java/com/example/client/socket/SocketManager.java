package com.example.client.socket;

import com.example.packet.Packet;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketManager extends Thread {
    private static SocketManager INSTANCE;

    private final int PORT = 6543;
    private final String DOMAIN = "localhost";

    private Socket socket = null;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private TextArea chatArea;

    private SocketManager() {
        try {
            socket = new Socket(DOMAIN, PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static SocketManager getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new SocketManager();
        }
        return INSTANCE;
    }

    public void sendMessage(Packet packet) {
        try {
            out.writeObject(packet);
            out.flush();  // send the message
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String login(Packet packet) {
        try {
            out.writeObject(packet);
            out.flush();
            String loginStatus = ((Packet) in.readObject()).getMessage();
            System.out.println("login status " + loginStatus);
            return loginStatus;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void setChatArea(TextArea chatArea){
        this.chatArea = chatArea;
    }

    @Override
    public void run() {
            while (true) {
                try {
                    Packet packet = (Packet) new ObjectInputStream(socket.getInputStream()).readObject();
                    System.out.println("PACKET: " + packet);
                    String message = String.format("%s: %s\n", packet.getUser().getUsername(), packet.getMessage());
                    this.chatArea.appendText(message);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
    }
}
