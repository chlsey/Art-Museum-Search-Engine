package interface_adapters.rating;

import java.io.IOException;
import java.util.List;

import entities.Artwork;
import use_case.rating.RatingInputBoundary;
import use_case.rating.RatingInputData;

/**
 * Rating Controller.
 */
public class RatingController {
    private final RatingInputBoundary ratingUseCaseInteractor;

    public RatingController(RatingInputBoundary ratingInputInteractor) {
        this.ratingUseCaseInteractor = ratingInputInteractor;
    }

    /**
     * Execute.
     * @param artwork artwork
     * @param rating rating
     * @throws IOException exception
     */
    public void execute(Artwork artwork, int rating) throws IOException {
        final RatingInputData ratingInputData = new RatingInputData(artwork.getId(), rating);
        ratingUseCaseInteractor.execute(ratingInputData);
    }

    /**
     * Execute.
     * @param artwork artwork
     * @param rating rating
     * @throws IOException exception
     */
    public void execute(String artwork, int rating) throws IOException {
        final RatingInputData rateInputData = new RatingInputData(
                artwork, rating);

        ratingUseCaseInteractor.execute(rateInputData);
    }

    public List<Artwork> getRatedArtworks() {
        return ratingUseCaseInteractor.getRatedArtworks();
    }
}
