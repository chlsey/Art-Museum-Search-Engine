package interface_adapters.rating;
import use_case.rating.RatingInputBoundary;
import use_case.rating.RatingInputData;
import use_case.rating.RatingInteractor;
import entities.Artwork;


public class RatingController {
    private final RatingInputBoundary ratingInputInteractor;

    public RatingController(RatingInputBoundary ratingInputInteractor) {
        this.ratingInputInteractor = ratingInputInteractor;
    }
    public void execute(int rating, Artwork artwork) {
        final RatingInputData ratingInputData = new RatingInputData(rating, artwork);
        ratingInputInteractor.execute(ratingInputData);
    }
}
