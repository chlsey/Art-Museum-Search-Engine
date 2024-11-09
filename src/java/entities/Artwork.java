package entities;

public class Artwork {
    private String title;
    private String artistName;
    private String timePeriod;
    private String gallery;
    private String imageUrl;
    private String[] keyWords;

    public Artwork(String title, String artistName, String timePeriod, String gallery,
                   String imageUrl, String[] keyWords) {
        this.title = title;
        this.artistName = artistName;
        this.timePeriod = timePeriod;
        this.gallery = gallery;
        this.imageUrl = imageUrl;
        this.keyWords = keyWords;
    }

    public String getTitle() { return title; }
    public String getArtistName() { return artistName; }
    public String getCompositionDate() { return timePeriod; }

    public String getGallery() { return gallery; }
    public String getImageUrl() { return imageUrl; }
    public String[] getKeyWords() { return keyWords; }
}