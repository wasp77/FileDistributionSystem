import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

public class FileDistributor extends Thread{
    private Socket connection;


    public FileDistributor(Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        System.out.println("thread: " + Thread.currentThread().getId());
        sendFile("test.txt");
        return;
    }

    private void sendFile(String path) {
        try {
            File testFile = new File(path);
            PrintWriter fileSender = new PrintWriter(connection.getOutputStream(), true);
            fileSender.write(new String(Files.readAllBytes(testFile.toPath())));
            fileSender.flush();
            fileSender.close();
            System.out.println("file sent");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
