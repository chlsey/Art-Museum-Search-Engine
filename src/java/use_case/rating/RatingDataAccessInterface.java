package use_case.rating;

public interface RatingDataAccessInterface {
    //void getAllRatings();
    int getRating();
    void setRating(int rating);
    void incrementRatingCount(int ratingValue);
    double calculateAverageRating();
}
