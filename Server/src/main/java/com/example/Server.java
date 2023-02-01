package com.example;

import com.example.packet.database.RunTimeDB;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 6543;
    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            Socket clientSocket = null;
            boolean isOpen = true;

            System.out.println("Server is running");
            while (isOpen) {
                clientSocket = serverSocket.accept(); // Keeps the program running until it gets a connection
                RunTimeDB.INSTANCE.clients.add(clientSocket);
                new Thread(new ServerThread(clientSocket)).start();
            }
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
