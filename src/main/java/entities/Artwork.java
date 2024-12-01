package entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Artwork class.
 */
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

    /**
     * Artwork class.
     * @param title the title
     * @param artistName the name
     * @param timePeriod the time
     * @param gallery the gallery
     * @param imageUrl the image url
     * @param keyWords the keyword
     * @param description the description
     * @param id the id
     */
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
    }

    public Artwork() {
        this.title = "";
        this.artistName = "";
        this.timePeriod = "";
        this.gallery = "";
        this.imageUrl = "";
        this.keyWords = "";
        this.description = "";
        this.id = "";
        this.favorited = false;
        this.comments = new ArrayList<>();
        this.rating = 0;
    }

    public String getTitle() {
        return title;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getCompositionDate() {
        return timePeriod;
    }

    public String getGallery() {
        return gallery;

    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getKeyWords() {
        return keyWords;
    }

    /**
     * CheckFavorited function.
     * @return if the artwork is favorited
     */
    public boolean checkFavorited() {
        return favorited;
    }

    /**
     * Set favorited.
     * @param status Status
     */

    public void setFavorited(boolean status) {
        if (status) {
            favorited = true;
        }
        else {
            favorited = false;
        }
    }

    public List<String> getComments() {
        return comments;
    }

    /**
     * AddComment.
     * @param comment comment
     */
    public void addComment(String comment) {
        this.comments.add(comment);
    }

    /**
     * Get last comment.
     * @return last comment
     */
    public String getLastComment() {
        String lastComment = "";
        if (comments.size() > 0) {
            lastComment = comments.get(comments.size() - 1);
        }
        return lastComment;
    }

    /**
     * Get rating.
     * @return rating
     */
    public int getRating() {
        final int value = rating;
        return value;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public void setGallery(String gallery) {
        this.gallery = gallery;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public String getTimePeriod() {
        return this.timePeriod;
    }

}
