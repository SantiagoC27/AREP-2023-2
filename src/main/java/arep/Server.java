package arep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import arep.Cache.MovieCache;
import arep.Service.WebService;

public class Server {
    public static void main(String[] args) throws IOException, MovieException {
        System.out.println("Server is up and running. Listening on port 35000...");
        ServerSocket serverSocket = null;
        MovieCache cache = new MovieCache(15 * 60 * 1000);
        WebService webService = new WebService(cache);
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        boolean runnig = true;
        while (runnig) {
            Socket clientSocket = serverSocket.accept();
            Thread t = new Thread(() -> {
                try {
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    String inputLine, outputLine = "";
                    boolean fristLine = true;
                    String path = "";
                    String movieName = "";

                    while ((inputLine = in.readLine()) != null) {
                        System.out.println("Received: " + inputLine);
                        if (fristLine) {
                            fristLine = false;
                            path = inputLine.split(" ")[1];
                            movieName = inputLine.split("=")[1].split(" ")[0];
                        }
                        if (!in.ready()) {
                            break;
                        }
                    }

                    if (path.startsWith("/movie?name=")) {
                        outputLine = respGetOK(webService.getMovie(movieName));
                    }

                    out.println(outputLine);

                    out.close();
                    in.close();
                    clientSocket.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            });
            t.start();
        }
        serverSocket.close();
        cache.closeTimer();
    }

    public static String respGetOK(String jContent) {
        return "HTTP/1.1 200 OK \r\n" +
                "Access-Control-Allow-Origin: * \r\n" +
                "Access-Control-Allow-Methods: GET, POST, OPTIONS, PUT, DELETE \r\n" +
                "Access-Control-Allow-Headers: Content-Type, Authorization \r\n" +
                "Content-Type: application/json \r\n" +
                "\r\n" +
                jContent;

    }
}
