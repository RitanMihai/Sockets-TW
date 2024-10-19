import packet.Command;
        import packet.Packet;
        import packet.User;
        import run_time_db.UserManagement;

        import java.io.IOException;
        import java.io.ObjectInputStream;
        import java.io.ObjectOutputStream;
        import java.net.Socket;
        import java.util.List;
        import java.util.Optional;

public class ClientThread extends Thread {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ClientThread(Socket clientConnection) {
        this.socket = clientConnection;
        try {
            this.out = new ObjectOutputStream(this.socket.getOutputStream()); // Initialize the output stream once
            this.in = new ObjectInputStream(this.socket.getInputStream()); // Initialize the input stream once
        } catch (IOException e) {
            throw new RuntimeException("Error initializing streams", e);
        }
    }

    @Override
    public void run() {
        try {
            boolean isRunning = true;
            while (isRunning) {
                Packet receivedPacket = (Packet) in.readObject();
                System.out.println("Received: " + receivedPacket);

                execute(receivedPacket);
            }
        } catch (ClassNotFoundException | IOException e) {
            System.out.println("Client disconnected or error: " + e.getMessage());
        } finally {
            closeConnection();
        }
    }

    private void execute(Packet receivedPacket) {
        Packet responsePacket = null;

        switch (receivedPacket.getCommand()) {
            case LOGIN -> {
                Optional<User> optionalUser = UserManagement.INSTANCE.login(receivedPacket.getUser());

                if (optionalUser.isPresent()) {
                    User loggedInUser = optionalUser.get();
                    loggedInUser.setSocket(this.socket);  // Store the socket in the user
                    loggedInUser.setOutStream(this.out);   // Store the ObjectOutputStream in the user

                    responsePacket = Packet.builder()
                            .message("Success")
                            .user(loggedInUser)  // Send back the logged-in user
                            .command(Command.LOGIN)
                            .build();
                } else {
                    responsePacket = Packet.builder().message("User not found").build();
                }
            }
            case REGISTER -> {
                // Handle registration (not implemented)
            }
            case MESSAGE_ALL -> {
                UserManagement.INSTANCE.broadcastMessage(receivedPacket);
            }
            case MESSAGE_INDIVIDUAL -> {
                // Handle individual messaging (not implemented)
            }
            default -> {
                responsePacket = Packet.builder().message("Invalid command").build();
            }
        }

        if (responsePacket != null) {
            sendPacket(responsePacket);
        }
    }

    private void sendPacket(Packet packet) {
        try {
            out.writeObject(packet);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException("Error sending packet", e);
        }
    }

    private void closeConnection() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            System.out.println("Error closing connection.");
        }
    }
}