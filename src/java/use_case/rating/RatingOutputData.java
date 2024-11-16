package use_case.rating;

public class RatingOutputData {
    private int rating;

    public RatingOutputData(int rating, double averageRating) {
        this.rating = rating;
    }
    public int getRating() {
        return rating;
    }

}
