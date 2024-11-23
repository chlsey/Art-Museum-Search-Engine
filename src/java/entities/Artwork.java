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
    private boolean favorited;
    private int totalScore;
    private int numRate;
    private List<String> comments;
    private String id;

    public Artwork(String title, String artistName, String timePeriod, String gallery,
                   String imageUrl, String keyWords, String description, String id) {
        this.title = title;
        this.artistName = artistName;
        this.timePeriod = timePeriod;
        this.description = description;
        this.gallery = gallery;
        this.imageUrl = imageUrl;
        this.keyWords = keyWords;
        this.favorited = false;
        this.comments = new ArrayList<>();
        this.totalScore = 0;
        this.numRate = 0;
        this.id = id;
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
    public String getLastComment() {
        if (comments.size() == 0) {
            return "";
        }
        return comments.get(comments.size() - 1);
    }
    public String getRating() {
        if (numRate == 0) {
            return "0";
        }
        double value = totalScore/numRate;
        String rating = String.format("%.3f",value);
        return rating; }
    public void newRating(float rating) {
        this.totalScore += rating;
        this.numRate += 1;
    }

    public String getDescription() {
        return description;
    }
    public String getId() {
        return id;
    }

    public int getnumRate() {
        return numRate;
    }

    public int getTotalScore() {
        return totalScore;
    }
}