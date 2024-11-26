package use_case.rating;
import entities.Artwork;

public class RatingInteractor implements RatingInputBoundary{
    private final RatingDataAccessInterface ratingDataAccessObject;
    private final RatingOutputBoundary ratingOutputBoundary;


    public RatingInteractor(RatingDataAccessInterface ratingDataAccessObject, RatingOutputBoundary ratingOutputBoundary) {
        this.ratingDataAccessObject = ratingDataAccessObject;
        this.ratingOutputBoundary = ratingOutputBoundary;
    }

    @Override
    public void execute(RatingInputData ratingInputData) {
        Artwork artwork = ratingInputData.getArtwork();
        Integer ratingValue = ratingInputData.getRating();
        if (ratingValue < 1) {
            ratingValue.toString() = "★ ☆ ☆ ☆ ☆";
            artwork.newRating(ratingValue);
        } else if (ratingValue > 5) {
            ratingValue = 5;
            artwork.newRating(ratingValue);
        }
        ratingDataAccessObject.incrementRatingCount(ratingValue);
       // double averageRating = ratingDataAccessObject.calculateAverageRating();
        RatingOutputData result = new RatingOutputData(ratingValue);
        ratingOutputBoundary.prepareRatingView(result);
    }

}
