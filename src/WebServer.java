import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
    public static void main(String[] args) throws Exception {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected to server");
                BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));
                String path;
                String[] parse;
                while ((path = br.readLine()) != null) {
                    System.out.println(path);
                    parse = path.split(" ");
                    if (parse[0].equalsIgnoreCase("GET"))
                        break;
                }
                parse = path.split(" ");
                path = "www" + parse[1];
                OutputStream os = clientSocket.getOutputStream();
                if (new File(path).exists()) {
                    os.write((parse[2] + " 200 OK\r\n").getBytes("UTF-8"));
                    os.write(("Content-type: text/html\r\n\r\n").getBytes("UTF-8"));
                    File file = new File(path);
                    BufferedReader brf = new BufferedReader(new FileReader(file));
                    System.out.println("");
                    String temp;
                    while ((temp = brf.readLine()) != null) {
                        os.write((temp).getBytes("UTF-8"));
                    }
                } else {
                    os.write((parse[2] + " 404 Not Found\r\n").getBytes("UTF-8"));
                    os.write(("Content-type: text/html\r\n\r\n").getBytes("UTF-8"));
                    File file = new File("www/FileNotFound.html");
                    BufferedReader brf = new BufferedReader(new FileReader(file));
                    System.out.println("");
                    String temp;
                    while ((temp = brf.readLine()) != null) {
                        os.write((temp).getBytes("UTF-8"));
                    }
                    brf.close();
                }
                clientSocket.close();
                br.close();
            }
        }
    }
}
