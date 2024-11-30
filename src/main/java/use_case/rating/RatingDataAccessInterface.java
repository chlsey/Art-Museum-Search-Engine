package use_case.rating;
import entities.Artwork;
import java.io.IOException;

public interface RatingDataAccessInterface {
//    void updateRating(Artwork artwork);
    void saveRating(Artwork artwork) throws IOException;
    int getRating();
    void setRating(int rating);
    void save(Artwork artwork) throws IOException;
    boolean contains(String id) throws IOException;
    Artwork getArtworkById(String id) throws IOException;
}
