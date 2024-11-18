package use_case.rating;

public interface RatingDataAccessInterface {
    //void getAllRatings();
    int getRating();
    void incrementRatingCount(int ratingValue);
    double calculateAverageRating();
}
