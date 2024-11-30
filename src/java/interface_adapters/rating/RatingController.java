package interface_adapters.rating;
import entities.Artwork;
import use_case.rating.RatingInputBoundary;
import use_case.rating.RatingInputData;
import use_case.rating.RatingInteractor;

import java.util.List;


public class RatingController {
    private final RatingInputBoundary ratingInputInteractor;

    public RatingController(RatingInputBoundary ratingInputInteractor) {
        this.ratingInputInteractor = ratingInputInteractor;
    }
    public void execute(int rating) {
        final RatingInputData ratingInputData = new RatingInputData(rating);
        ratingInputInteractor.execute(ratingInputData);
    }

    public List<Artwork> getRatedArtworks() {
        return ratingInputInteractor.getRatedArtworks();
    }
}
