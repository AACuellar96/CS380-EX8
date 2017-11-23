import java.net.ServerSocket;
import java.net.Socket;

public class Webserver {
    public static void main(String[] args) throws Exception {
        try (ServerSocket serverSocket = new ServerSocket(8080) ){
            Socket clientSocket = serverSocket.accept();
        }
    }
}
