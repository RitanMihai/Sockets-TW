import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int PORT = 6543;

    public void start() {
        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(PORT);
                Socket clientSocket = null;
                boolean isOpen = true;

                System.out.println("Server is running");
                while (isOpen) {
                    clientSocket = serverSocket.accept(); // Keeps the program running until it gets a connection
                    new Thread(new ServerThread(clientSocket)).start();
                }
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
