package interface_adapters.rating;

import entities.Artwork;
import use_case.rating.RatingInputBoundary;
import use_case.rating.RatingInputData;
import use_case.rating.RatingInputBoundary;

import java.io.IOException;

import java.util.List;

public class RatingController {
    private final RatingInputBoundary ratingUseCaseInteractor;

    public RatingController(RatingInputBoundary ratingInputInteractor) {
        this.ratingUseCaseInteractor = ratingInputInteractor;
    }
    public void execute(Artwork artwork, int rating) throws IOException {
        final RatingInputData ratingInputData = new RatingInputData(artwork.getId(), rating);
        ratingUseCaseInteractor.execute(ratingInputData);
    }

    public void execute(String artwork, int rating) throws IOException {
        final RatingInputData rateInputData = new RatingInputData(
                artwork, rating);

        ratingUseCaseInteractor.execute(rateInputData);
    }

    public List<Artwork> getRatedArtworks() {
        return ratingUseCaseInteractor.getRatedArtworks();
    }
}
