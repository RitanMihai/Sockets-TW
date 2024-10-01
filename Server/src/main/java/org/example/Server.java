package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    /* Arbitrary port number */
    public static final int PORT = 6543;

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            Socket clientConnection = null;

            boolean isRunning = true;

            System.out.println("Server is running");
            /* An infinite loop is necessary in order keep the server awake */
            while(isRunning){
                /* Accepts connections */
                clientConnection = serverSocket.accept();
                /* Make a new thread for each client */
                new Thread(new ServerThread(clientConnection)).start();
            }

        } catch (IOException e) { /* Failed to create the Terminal */
            throw new RuntimeException(e);
        }
    }
}
