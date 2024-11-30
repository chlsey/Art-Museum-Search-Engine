package use_case.rating;

import entities.Artwork;

import java.io.IOException;
import java.util.List;

public interface RatingDataAccessInterface {
    //void getAllRatings();
    int getRating(Artwork artwork) throws IOException;;
    void setRating(int rating);

    List<Artwork> getRatedArtworks();
}
