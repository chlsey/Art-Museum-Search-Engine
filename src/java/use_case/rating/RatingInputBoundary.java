package use_case.rating;

import java.io.IOException;
import entities.Artwork;

import java.util.List;

public interface RatingInputBoundary {
    void execute(RatingInputData ratingOutputData) throws IOException;

    List<Artwork> getRatedArtworks();
}
