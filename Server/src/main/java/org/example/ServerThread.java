package org.example;

import org.example.packet.Packet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread extends Thread {
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket socket;

    public ServerThread(Socket clientConnection) {
        try {
            this.socket = clientConnection;
            this.in = new ObjectInputStream(clientConnection.getInputStream());
            this.out = new ObjectOutputStream(clientConnection.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try{
            boolean isRunning = true;
            while(isRunning) {
                Packet receivedPacket = (Packet) this.in.readObject();
                System.out.println("Received: " + receivedPacket.getMessage());

                /* Choose what to do with the received data */
                execute(receivedPacket.getMessage());
            }
        } catch (ClassNotFoundException | IOException e) {
            System.out.println("Client disconnected or error: " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            // Close resources in case of disconnection or exception
            try {
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void execute(String message) {
        Packet packet = switch (message) {
            case "Hello" -> new Packet("Hello There");
            case "How are you?" -> new Packet("I am fine");
            default -> new Packet("Can't understand you :/");
        };

        try{
            this.out.writeObject(packet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
