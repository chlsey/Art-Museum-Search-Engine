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
    private String description;
    private String id;
    private boolean favorited;
    private int rating;
    private List<String> comments;

    public Artwork(String title, String artistName, String timePeriod, String gallery,
                   String imageUrl, String keyWords, String description, String id) {
        this.title = title;
        this.artistName = artistName;
        this.timePeriod = timePeriod;
        this.description = description;
        this.gallery = gallery;
        this.imageUrl = imageUrl;
        this.keyWords = keyWords;
        this.id = id;
        this.favorited = false;
        this.comments = new ArrayList<>();
        this.rating = 0;
    }


    public String getTitle() { return title; }
    public String getArtistName() { return artistName; }
    public String getCompositionDate() { return timePeriod; }

    public String getGallery() { return gallery; }
    public String getImageUrl() { return imageUrl; }
    public String getKeyWords() { return keyWords; }
    public boolean checkFavorited() { return favorited; }
    public void setFavorited() {
        if (favorited) {
            favorited = false;
        } else {
            favorited = true;
        }
    }
    public List<String> getComments() {
        return comments;
    }
    public void addComment(String comment) {
        this.comments.add(comment);
    }
    public String getLastComment() {
        if (comments.size() == 0) {
            return "";
        }
        return comments.get(comments.size() - 1);
    }
    public String getRating() {
        Integer value = rating;
        String rating = value.toString();
        return rating; }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }
    public String getId() {
        return id;
    }

}