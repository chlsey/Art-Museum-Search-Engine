package use_case.rating;

import entities.Artwork;

public class RatingInputData {
    private final int rating;
    private final Artwork artwork;

    public RatingInputData(int rating, Artwork artwork) {
        this.rating = rating;
        this.artwork = artwork;
    }
    public int getRating() {
        return rating;
    }
    public Artwork getArtwork() {
        return artwork;
    }
}



