package arep.Cache;

import junit.framework.TestCase;

public class MovieCacheTest extends TestCase {

    public void testGetCachedMovieAndputCache() {
        //Que si traiga la data, insertar objetos dentro de la cache y verificar que la informacion sea correcta
        MovieCache cache = new MovieCache(15 * 60 * 1000);

        String[] movieTitles = {
                "Thor", "Avengers", "Iron Man", "Spider-Man", "Black Panther",
                "Wonder Woman", "Guardians of the Galaxy", "Captain America", "Deadpool",
                "Doctor Strange", "The Dark Knight", "X-Men", "The Incredibles",
                "Jurassic Park", "Star Wars", "Toy Story", "The Lion King",
                "Harry Potter", "Pirates of the Caribbean", "Frozen"
        };

        String[] movieValues = {
                "asdasd1", "asdasd2", "asdasd3", "asdasd4", "asdasd5",
                "asdasd6", "asdasd7", "asdasd8", "asdasd9", "asdasd10",
                "asdasd11", "asdasd12", "asdasd13", "asdasd14", "asdasd15",
                "asdasd16", "asdasd17", "asdasd18", "asdasd19", "asdasd20"
        };

        for (int i = 0; i < movieTitles.length; i++) {
            String key = movieTitles[i];
            String value = movieValues[i];
            cache.putCache(key, value);
        }

        for (int i = 0; i < movieTitles.length; i++){
            assertEquals(movieValues[i],cache.getCachedMovie(movieTitles[i]));
        }
    }

    public void testCleaningTask() throws InterruptedException {
        //insertar varios valores luego dormir la funcion por un tiempo mayor al que se envio, validar que la cache esta vacia
        MovieCache cache = new MovieCache(2 * 1000);

        String[] movieTitles = {
                "Thor", "Avengers", "Iron Man", "Spider-Man", "Black Panther",
                "Wonder Woman", "Guardians of the Galaxy", "Captain America", "Deadpool",
                "Doctor Strange", "The Dark Knight", "X-Men", "The Incredibles",
                "Jurassic Park", "Star Wars", "Toy Story", "The Lion King",
                "Harry Potter", "Pirates of the Caribbean", "Frozen"
        };

        String[] movieValues = {
                "asdasd1", "asdasd2", "asdasd3", "asdasd4", "asdasd5",
                "asdasd6", "asdasd7", "asdasd8", "asdasd9", "asdasd10",
                "asdasd11", "asdasd12", "asdasd13", "asdasd14", "asdasd15",
                "asdasd16", "asdasd17", "asdasd18", "asdasd19", "asdasd20"
        };

        for (int i = 0; i < movieTitles.length; i++) {
            String key = movieTitles[i];
            String value = movieValues[i];
            cache.putCache(key, value);
        }

        Thread.sleep(3 * 1000);

        for (int i = 0; i < movieTitles.length; i++){
            assertNull(cache.getCachedMovie(movieTitles[i]));
        }
    }

}