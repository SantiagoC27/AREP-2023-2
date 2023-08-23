package arep.Cache;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

public class MovieCache implements ICache{

    private final long CACHE_DURATION;
    private Timer timer;

    private Map<String, String> cache;

    public MovieCache(long tiempo) {
        cache = new ConcurrentHashMap<>();
        CACHE_DURATION = tiempo;
        CleaningTask();
    }

    public String getCachedMovie(String movieName) {
        return cache.get(movieName);
    }

    public void putCache(String movieName, String jsonData) {
        cache.put(movieName, jsonData);
    }

    private void CleaningTask() {
        this.timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                clearCache();
            }
        }, CACHE_DURATION, CACHE_DURATION);
    }

    private void clearCache() {
        cache.clear();
    }

    public void closeTimer(){
        timer.cancel();
    }
}
