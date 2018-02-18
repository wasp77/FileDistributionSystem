import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Peer {
    private static final int PORT = 7080;
    private static ArrayList<InetAddress> join() {
        System.out.println("joining");
        try {
            Socket serverConnect = new Socket("localhost", PORT);
            PrintWriter out = new PrintWriter(serverConnect.getOutputStream(), true);
            out.println("join");
            ObjectInputStream tableReceiver = new ObjectInputStream(serverConnect.getInputStream());
            return (ArrayList<InetAddress>) tableReceiver.readObject();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Problems connection to server");
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error reading in address table");
            return null;
        }
    }

    private static void leave() {
        System.out.println("leaving");
        try {
            Socket serverConnect = new Socket("localhost", PORT);
            PrintWriter out = new PrintWriter(serverConnect.getOutputStream(), true);
            out.println("leave");
            out.close();
            serverConnect.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ArrayList<InetAddress> addressTable = join();
        assert addressTable != null;
        for (InetAddress i: addressTable) System.out.println(i);
        Scanner input = new Scanner(System.in);
        String message;
        while ((message = input.nextLine()) != null) {
            if (message.equals("leave")) {
                leave();
                break;
            }
        }

    }

}
