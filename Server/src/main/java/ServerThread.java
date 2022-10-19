import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread extends Thread {
    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;

    public ServerThread(Socket socket) {
        try {
            //For receiving and sending data
            this.in = new ObjectInputStream(socket.getInputStream());
            this.out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            Packet receivedPacket = (Packet) this.in.readObject();
            System.out.println("Received: " + receivedPacket.message);
            execute(receivedPacket.message);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void execute(String message) {
        Packet packet = switch (message) {
            case "Hello" -> new Packet("Hello There");
            case "How are you?" -> new Packet("I'm fine");
            case "Bye" -> new Packet("Bye");
            default -> new Packet("Can't understand you :/");
        };

        try {
            this.out.writeObject(packet);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
