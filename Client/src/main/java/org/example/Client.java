package org.example;

import org.example.packet.Packet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    public static final int PORT = 6543;
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private volatile boolean isRunning = true; // Volatile to ensure visibility across threads

    public void start() throws IOException, ClassNotFoundException {
       /* Connect to server */
        try {
            socket = new Socket("localhost", PORT);
        } catch (UnknownHostException e){
            System.err.println("Failed to connect to the server");
        }

        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());

        /* Create and start a thread to handle server messages */
        new Thread(()->{
                Packet receivePacket = null; // Receive message from server
                try {
                    while(isRunning){
                    receivePacket = (Packet) inputStream.readObject();
                    System.out.println("Server: " + receivePacket.getMessage());
                    }
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } finally {
                    try {
                        this.socket.close(); // Ensure socket is closed
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
            }
        }).start();

        /* Read Client Data on separate Thread */
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);

            while(isRunning) {
                System.out.println("Your message: ");
                String message = scanner.nextLine();

                if (message.equalsIgnoreCase("Close")) {
                    isRunning = false;
                }

                Packet packet = new Packet(message);

                try {
                    outputStream.writeObject(packet);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            // Close resources
            try {
                this.socket.close();
                scanner.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
