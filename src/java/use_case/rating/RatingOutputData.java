package use_case.rating;

public class RatingOutputData {
    private int rating;
    private double averageRating;

    public RatingOutputData(int rating, double averageRating) {
        this.rating = rating;
        this.averageRating = averageRating;
    }

    public int getRating() {
        return rating;
    }

    public double getAverageRating() {
        return averageRating;
    }
}
