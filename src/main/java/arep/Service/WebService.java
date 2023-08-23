package arep.Service;

import arep.Cache.ICache;
import arep.Cache.MovieCache;
import arep.MovieException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebService {

    private ICache cache;

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
}
