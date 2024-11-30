package use_case.rating;
import entities.Artwork;
import java.io.IOException;

import entities.Artwork;

import java.io.IOException;
import java.util.List;

public interface RatingDataAccessInterface {
//    void updateRating(Artwork artwork);
    void saveRating(Artwork artwork) throws IOException;
    int getRating();
    void setRating(int rating);
    void save(Artwork artwork) throws IOException;
    boolean contains(String id) throws IOException;
    Artwork getArtworkById(String id) throws IOException;
    //void getAllRatings();
    int getRating(Artwork artwork) throws IOException;;
    void setRating(String id, int rating);

    List<Artwork> getRatedArtworks();
}
