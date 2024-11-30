package use_case.rating;
import data.InMemoryDataAccessObject;
import entities.Artwork;
import use_case.favorite.FavoriteOutputData;

import java.io.IOException;

import entities.Artwork;

import java.util.List;

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

    @Override
    public List<Artwork> getRatedArtworks() {
        return ratingDataAccessObject.getRatedArtworks();
    }

}
