package entities;

public class ArtworkFactory {

    public static Artwork createArtwork(String title, String artistName, String timePeriod,
                                        String gallery, String imageUrl,
                                        String keyWords, String description) {
        return new Artwork(title, artistName, timePeriod, gallery, imageUrl, keyWords, description);
    }


    public static Artwork createArtwork(String title, String artistName) {
        return new Artwork(title, artistName, "Unknown Time Period",
                "Unknown Gallery", null, String.format("%s, %s", title, artistName), "No description");
    }
}
