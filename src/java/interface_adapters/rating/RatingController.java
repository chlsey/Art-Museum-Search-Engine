package interface_adapters.rating;

import entities.Artwork;
import use_case.rating.RatingInputBoundary;
import use_case.rating.RatingInputData;
import use_case.rating.RatingInputBoundary;

import java.io.IOException

import java.util.List;

public class RatingController {
    private final RatingInputBoundary ratingUseCaseInteractor;

    public RatingController(RatingInputBoundary ratingUseCaseInteractor) {
        this.ratingUseCaseInteractor = ratingUseCaseInteractor;
    }

    public void execute(String artwork, int rating) throws IOException {
        final RatingInputData rateInputData = new RatingInputData(
                artwork, rating);

        ratingUseCaseInteractor.execute(rateInputData);
    }
}
