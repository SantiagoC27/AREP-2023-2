package arep.Cache;

public interface ICache {
    String getCachedMovie(String movieName);
    void putCache(String movieName, String jsonData);
}
