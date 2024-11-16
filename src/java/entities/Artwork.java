package entities;

public class Artwork {
    private String title;
    private String artistName;
    private String timePeriod;
    private String gallery;
    private String imageUrl;
    private String keyWords;
    private String comment;
    private boolean favorited;

    public Artwork(String title, String artistName, String timePeriod, String gallery,
                   String imageUrl, String keyWords) {
        this.title = title;
        this.artistName = artistName;
        this.timePeriod = timePeriod;
        this.gallery = gallery;
        this.imageUrl = imageUrl;
        this.keyWords = keyWords;
        this.comment = "";
        this.favorited = false;
    }

    public Artwork(String title, String artistName, String timePeriod, String gallery,
                   String imageUrl, String keyWords, String comment) {
        this.title = title;
        this.artistName = artistName;
        this.timePeriod = timePeriod;
        this.gallery = gallery;
        this.imageUrl = imageUrl;
        this.keyWords = keyWords;
        this.comment = comment;
    }

    public String getTitle() { return title; }
    public String getArtistName() { return artistName; }
    public String getCompositionDate() { return timePeriod; }

    public String getGallery() { return gallery; }
    public String getImageUrl() { return imageUrl; }
    public String getKeyWords() { return keyWords; }
    public String getComment() { return comment; }
    public boolean checkFavorited() { return favorited; }
    public void setFavorited() { favorited = !favorited; }
}