package use_case.rating;
import entities.Artwork;

public class RatingOutputData {
    private int rating;


    public RatingOutputData(int rating) {
        this.rating = rating;
    }
    public int getRating() {
        return rating;
    }

}
