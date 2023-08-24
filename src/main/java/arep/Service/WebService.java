package arep.Service;

import arep.Cache.ICache;
import arep.MovieException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WebService {

    private final ICache cache;

    public WebService(ICache cache) {
        this.cache = cache;
    }

    public String getMovie(String movieName) throws IOException, MovieException {
        String cachedData = cache.getCachedMovie(movieName);
        if (cachedData != null) {
            return cachedData;
        }

        String apiKey = "a445b9e2";
        StringBuilder response = new StringBuilder();

        if (movieName == null || movieName.isEmpty()) {
            throw new MovieException();
        }

        URL consultURL = new URL("http://www.omdbapi.com/?apikey=" + apiKey + "&t=" + movieName);
        HttpURLConnection connection = (HttpURLConnection) consultURL.openConnection();

        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            String jsonResponse = response.toString();
            cache.putCache(movieName, jsonResponse);
            return jsonResponse;
        } else {
            System.out.println("Request failed with response code: " + responseCode);
            return null;
        }
    }

    public String readFiles(String fileName) {
        StringBuilder content = new StringBuilder();
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("public/" + fileName)) {
            if (inputStream == null) throw new IOException();
            Scanner scanner = new Scanner(inputStream, "UTF-8");

            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine()).append("\n");
            }

        } catch (IOException ignored) {
        }

        return content.toString();
    }
}
