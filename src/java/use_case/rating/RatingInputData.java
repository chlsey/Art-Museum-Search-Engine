package use_case.rating;

import entities.Artwork;

public class RatingInputData {
    private final int rating;
    private final String artworkId;

    public RatingInputData(String artworkId, int rating) {
        this.artworkId = artworkId;
        this.rating = rating;

    }

    public String getArtworkId() {
        return artworkId;
    }
    public int getRating() {
        return rating;
    }
}



