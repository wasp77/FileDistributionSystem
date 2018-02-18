import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final int PORT = 7777;
    private static final String HOSTNAME = "localhost";
    public static void main(String[] args) {
        try {
            Socket connection = new Socket(HOSTNAME, PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            FileWriter file = new FileWriter("received.txt");
            PrintWriter fileWrite = new PrintWriter(file);
            String fileContents;
            System.out.println("writing file...");
            while ((fileContents = in.readLine()) != null) {
                System.out.println("...");
                System.out.println(fileContents);
                fileWrite.println(fileContents);
            }
            file.close();
            fileWrite.close();
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
