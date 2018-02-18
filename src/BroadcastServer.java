import java.io.*;
import java.net.*;

public class BroadcastServer {
    private static DatagramSocket socket;
    private static byte[] buf = new byte[256];
    private static final int PORT = 7777;

    public static void main(String[] args) {
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        try {
            socket = new DatagramSocket(4445);
            socket.receive(packet);
            String received = new String(packet.getData(), 0, packet.getLength());
            if (ack(received)) {
                sendData(packet.getAddress());
            } else {
                System.out.println("file not available");
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static boolean ack(String received) {
        boolean check = new File(received).exists();
        if (check) {
            System.out.println(received + " exits");
            return true;
        } else {
            System.out.println(received + " does not exit");
            return false;
        }
    }

    private static void sendData(InetAddress hostname) {
        try {
            //System.out.println(hostname);
            Socket clientConnect = new Socket("0.0.0.0", PORT);

            File testFile = new File("test.txt");
            byte[] fileBytes = new byte[(int)testFile.length()];
            BufferedInputStream fileStream = new BufferedInputStream(new FileInputStream(testFile));
            fileStream.read(fileBytes, 0, fileBytes.length);
            OutputStream fileOut = clientConnect.getOutputStream();

            PrintWriter out = new PrintWriter(clientConnect.getOutputStream(), true);
            out.println(fileBytes.length);
            fileOut.write(fileBytes, 0, fileBytes.length);
            fileOut.flush();

            out.println(fileBytes.length);
            for (int i = 0; i< fileBytes.length; i++) {
                System.out.println(fileBytes[i]);
            }
            fileOut.write(fileBytes, 0, fileBytes.length);
            fileOut.flush();
            System.out.println("file sent");
        } catch (IOException e) {
            e.getCause();
            e.printStackTrace();
        }

    }
}


