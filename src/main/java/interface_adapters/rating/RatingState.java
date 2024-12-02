package interface_adapters.rating;

/**
 * Rating state class.
 */
public class RatingState {
    private int rating = 0;

    public RatingState() {
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getRating() {
        return this.rating;
    }
}
