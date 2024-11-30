package use_case.rating;

import entities.Artwork;

public class RatingInputData {
    private final int rating;

    private final String id;

    public RatingInputData(String id, int rating) {
        this.id = id;
        this.rating = rating;

    }

    public String getArtworkId() {
        return id;
    }
    public int getRating() {
        return rating;
    }

    public String getId() {
        return id;
    }
}



