package use_case.rating;
import entities.*;
public interface RatingDataAccessInterface {
    void getAllRatings();
    int getRatingCount(int ratingValue);
    void incrementRatingCount(int ratingValue);
    double calculateAverageRating();
}
