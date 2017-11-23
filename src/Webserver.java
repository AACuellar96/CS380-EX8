import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Webserver {
    public static void main(String[] args) throws Exception {
        try (ServerSocket serverSocket = new ServerSocket(8080) ){
            Socket clientSocket = serverSocket.accept();
            BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(),"UTF-8"));
            String inp;
            String path;
            String [] parse;
            while(true) {
                path = br.readLine();
                parse = path.split(" ");
                if(parse[0].equalsIgnoreCase("GET"))
                    break;
            }
            path = "www" + parse[1];
            PrintStream out = new PrintStream(clientSocket.getOutputStream(), true, "UTF-8");
            if(new File(path).exists()){
                out.println(parse[2] + " 200 OK");
                out.println("Content-type: text/html");
                out.println("Content-length: " + new File(path).length());
            }
            else{
                out.println(parse[2] + " 404 Not Found");
                out.println("Content-type: text/html");
                out.println("Content-length: " + new File("www/FileNotFound.html").length());
            }
        }
    }
}
