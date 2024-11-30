package use_case.rating;
import entities.Artwork;

import java.io.IOException;

public class RatingInteractor implements RatingInputBoundary{
    private final RatingDataAccessInterface ratingDataAccessObject;
    private final RatingOutputBoundary ratingOutputBoundary;


    public RatingInteractor(RatingDataAccessInterface ratingDataAccessObject, RatingOutputBoundary ratingOutputBoundary) {
        this.ratingDataAccessObject = ratingDataAccessObject;
        this.ratingOutputBoundary = ratingOutputBoundary;
    }

    @Override
    public void execute(RatingInputData ratingInputData) throws IOException {
        if (ratingDataAccessObject.contains(ratingInputData.getArtworkId())) {
            Artwork rating = ratingDataAccessObject.getArtworkById(ratingInputData.getArtworkId());
            rating.setRating(ratingInputData.getRating());
            ratingDataAccessObject.save(rating);
            int art = ratingInputData.getRating();
            ratingOutputBoundary.prepareSuccessView(new RatingOutputData(art));
            ratingOutputBoundary.prepareSuccessView(new RatingOutputData(art));
        } else {
            int art = ratingInputData.getRating();
            Artwork rating = ratingDataAccessObject.getArtworkById(ratingInputData.getArtworkId());
            rating.setRating(ratingInputData.getRating());
            ratingDataAccessObject.save(rating);
            ratingOutputBoundary.prepareSuccessView(new RatingOutputData(art));
        }

    }

}
