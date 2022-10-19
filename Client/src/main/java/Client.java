import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static final int PORT = 6543;

    public void start() throws Exception {
        System.out.println("Say something and the message will be sent to the server: ");
        Socket socket = null;

        //For receiving and sending data
        boolean isClose = false;
        while (!isClose) {
            socket = new Socket("localhost", PORT);

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

            Scanner scanner = new Scanner(System.in);
            String message = scanner.nextLine();

            if (message.equals("Bye")) {
                isClose = true;
            }

            Packet packet = new Packet(message);
            outputStream.writeObject(packet);

            Packet recivePacket = (Packet) inputStream.readObject();
            System.out.println(recivePacket.message);
        }
        socket.close();
    }
}
