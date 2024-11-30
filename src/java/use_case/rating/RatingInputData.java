package use_case.rating;

import entities.Artwork;

public class RatingInputData {
    private final int rating;
    private final Artwork artwork;

    public RatingInputData(Artwork artwork, int rating) {
        this.artwork = artwork;
        this.rating = rating;
    }
    public int getRating() {
        return rating;
    }
}



