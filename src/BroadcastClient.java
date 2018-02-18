import java.io.*;
import java.net.*;

public class BroadcastClient {
    private static DatagramSocket socket = null;
    private static final int PORT = 7777;

    public static void main(String[] args) {
        try {
            broadcast("test.txt", InetAddress.getByName("255.255.255.255"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void broadcast(String broadcastMessage, InetAddress address) throws IOException {
        socket = new DatagramSocket();
        socket.setBroadcast(true);

        byte[] buffer = broadcastMessage.getBytes();

        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 4445);
        socket.send(packet);
        socket.close();
        acceptConnection();
    }

    private static void acceptConnection() {
        try {
            ServerSocket server = new ServerSocket(PORT);
            Socket connection = server.accept();

            InputStream is = connection.getInputStream();
            FileOutputStream fos = new FileOutputStream("received.txt");
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            int length = Integer.parseInt(in.readLine());
            System.out.println(length);
            byte[] file = new byte[length];
            for (int i = 0; i< length; i++) {
                System.out.println(file[i]);
            }
            is.read(file, 0, length);
            bos.write(file, 0, length);
            bos.close();
            System.out.println("file written");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
