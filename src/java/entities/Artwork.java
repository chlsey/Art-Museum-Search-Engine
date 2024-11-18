package entities;

import java.util.ArrayList;
import java.util.List;

public class Artwork {
    private String title;
    private String artistName;
    private String timePeriod;
    private String gallery;
    private String imageUrl;
    private String keyWords;
    private boolean favorited;
    private List<String> comments;

    public Artwork(String title, String artistName, String timePeriod, String gallery,
                   String imageUrl, String keyWords) {
        this.title = title;
        this.artistName = artistName;
        this.timePeriod = timePeriod;
        this.gallery = gallery;
        this.imageUrl = imageUrl;
        this.keyWords = keyWords;
        this.favorited = false;
        this.comments = new ArrayList<>();
    }


    public String getTitle() { return title; }
    public String getArtistName() { return artistName; }
    public String getCompositionDate() { return timePeriod; }

    public String getGallery() { return gallery; }
    public String getImageUrl() { return imageUrl; }
    public String getKeyWords() { return keyWords; }
    public boolean checkFavorited() { return favorited; }
    public void setFavorited() { favorited = !favorited; }
    public List<String> getComments() {
        return comments;
    }
    public void addComment(String comment) {
        this.comments.add(comment);
    }

    public String getDescription() {
        String description = "implement later";
        return description;
    }
}