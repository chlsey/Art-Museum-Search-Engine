package use_case.rating;
import entities.Artwork;

public class RatingOutputData {
    private String rating;


    public RatingOutputData(Artwork artwork) {
        rating = artwork.getRating();
    }

    public String getRating() {
        return rating;
    }

}
