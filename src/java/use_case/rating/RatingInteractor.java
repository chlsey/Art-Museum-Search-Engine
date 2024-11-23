package use_case.rating;

public class RatingInteractor implements RatingInputBoundary{
    private final RatingDataAccessInterface ratingDataAccessObject;
    private final RatingOutputBoundary ratingOutputBoundary;


    public RatingInteractor(RatingDataAccessInterface ratingDataAccessObject, RatingOutputBoundary ratingOutputBoundary) {
        this.ratingDataAccessObject = ratingDataAccessObject;
        this.ratingOutputBoundary = ratingOutputBoundary;
    }

    @Override
    public void execute(RatingInputData ratingOutputData) {
        int ratingValue = (int) ratingOutputData.getRating();
        if (ratingValue < 1) {
            ratingValue = 1;
        } else if (ratingValue > 5) {
            ratingValue = 5;
        }
        ratingDataAccessObject.incrementRatingCount(ratingValue);
        double averageRating = ratingDataAccessObject.calculateAverageRating();
        RatingOutputData result = new RatingOutputData(ratingValue, averageRating);
        ratingOutputBoundary.prepareRatingView(result);
    }

}
