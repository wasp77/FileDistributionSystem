import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class P2PServer {
    private static ArrayList<InetAddress> addressTable = new ArrayList<InetAddress>();
    private static final int PORT = 7080;
    public static void main (String[] args) {
        acceptConnections();
    }

    private static void acceptConnections() {
        try {
            ServerSocket server = new ServerSocket(PORT);
            while (true) {
                Socket connection = server.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String clientAction = in.readLine();
                if (clientAction.equals("join")) {
                    System.out.println("adding peer");
                    ObjectOutputStream tableSender = new ObjectOutputStream(connection.getOutputStream());
                    tableSender.writeObject(addressTable);
                    tableSender.flush();
                    tableSender.close();
                    addPeer(connection.getInetAddress());
                    System.out.println(addressTable.size());
                } else if (clientAction.equals("leave")) {
                    System.out.println("removing peer");
                    addressTable.remove(connection.getInetAddress());
                    System.out.println(addressTable.size());
                    for (InetAddress i: addressTable) System.out.println(i);
                }
                connection.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addPeer(InetAddress newPeer) {
        if (!addressTable.contains(newPeer)) {
            addressTable.add(newPeer);
        }
    }
}

