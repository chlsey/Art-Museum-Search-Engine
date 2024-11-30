package use_case.rating;
import entities.Artwork;
import java.io.IOException;

import entities.Artwork;

import java.io.IOException;
import java.util.List;

public interface RatingDataAccessInterface {
//    void updateRating(Artwork artwork);
    void save(Artwork artwork) throws IOException;
    boolean contains(String id) throws IOException;
    Artwork getArtworkById(String id) throws IOException;
    List<Artwork> getRatedArtworks();

    void updateRating(String id, int rating) throws IOException;
}
