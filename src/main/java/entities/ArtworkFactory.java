package entities;

/**
 * ArtworkFactory class.
 */

public class ArtworkFactory {

    /**
     * Artwork.
     * @param title the title
     * @param artistName the name
     * @param timePeriod the time
     * @param gallery the galery
     * @param imageUrl image
     * @param keyWords keyword
     * @param description description
     * @param id id
     * @return a arwork object
     */
    public static Artwork createArtwork(String title, String artistName, String timePeriod,
                                        String gallery, String imageUrl,
                                        String keyWords, String description, String id) {
        return new Artwork(title, artistName, timePeriod, gallery, imageUrl, keyWords, description, id);
    }
}
