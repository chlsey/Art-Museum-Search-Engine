package use_case.rating;

import entities.Artwork;

import java.util.List;

public interface RatingInputBoundary {
    void execute(RatingInputData ratingOutputData);

    List<Artwork> getRatedArtworks();
}
