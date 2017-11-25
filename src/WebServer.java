import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
    public static void main(String[] args) throws Exception {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            while (true) {
                System.out.println("Client connecting to server");
                Socket clientSocket = serverSocket.accept();
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
                PrintStream out = new PrintStream(clientSocket.getOutputStream(), true, "UTF-8");
                if (new File(path).exists()) {
                    out.println(parse[2] + " 200 OK");
                    out.println("Content-type: text/html");
                    File file = new File(path);
                    BufferedReader brf = new BufferedReader(new FileReader(file));
                    out.println("Content-length: " + file.length());
                    System.out.println("");
                    String temp;
                    while ((temp = brf.readLine()) != null) {
                        out.println(temp);
                    }
                } else {
                    out.println(parse[2] + " 404 Not Found");
                    out.println("Content-type: text/html");
                    File file = new File("www/FileNotFound.html");
                    BufferedReader brf = new BufferedReader(new FileReader(file));
                    out.println("Content-length: " + file.length());
                    System.out.println("");
                    String temp;
                    while ((temp = brf.readLine()) != null) {
                        out.println(temp);
                    }
                    brf.close();
                }
                clientSocket.close();
                br.close();
            }
        }
    }
}
