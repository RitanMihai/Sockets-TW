package com.example;


import com.example.packet.Command;
import com.example.packet.database.RunTimeDB;
import com.example.packet.Packet;
import com.example.packet.database.User;

import java.io.*;
import java.net.Socket;
import java.util.Optional;

public class ServerThread extends Thread {
    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;
    private Socket socket = null;

    public ServerThread(Socket socket) {
        try {
            //For receiving and sending data
            this.in = new ObjectInputStream(socket.getInputStream());
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.socket = socket;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            while (true) {
                Packet receivedPacket = (Packet) this.in.readObject();
                System.out.println("Received: " + receivedPacket + "\nFrom " + socket);
                command(receivedPacket);
            }//execute(receivedPacket);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void command(Packet packet) throws IOException {
        switch (packet.getCommand()) {
            case LOGIN -> {
                /* Find user in runtime database */
                Optional<User> optionalUser = RunTimeDB.INSTANCE.users.stream().filter(
                        user -> user.getUsername().equals(packet.getUser().getUsername())
                                && user.getPassword().equals(packet.getUser().getPassword())
                ).findFirst();

                /* If any return "ok" otherwise "wrong" */
                if(optionalUser.isPresent()) {
                    packet.setMessage("ok");
                    out.writeObject(packet);
                } else {
                    packet.setMessage("wrong");
                    out.writeObject(packet);
                }
            }
            case MESSAGE -> {
                this.broadcast(packet);
            }
        }
    }

    private void broadcast(Packet packet) {
        RunTimeDB.INSTANCE.clients.forEach(
                socket -> {
                    try {
                        //System.out.println("\nWrite to: " + socket + "\nThe message " + packetCopy + "\n");
                        new ObjectOutputStream(socket.getOutputStream()).writeObject(packet);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }
}