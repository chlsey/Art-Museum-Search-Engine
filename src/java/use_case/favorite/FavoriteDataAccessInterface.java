package use_case.favorite;

import entities.Artwork;

import java.io.IOException;
import java.util.List;

public interface FavoriteDataAccessInterface {
    /**
     * Queries the DB to find a list of artworks that match the search message.
     * @param artwork is the artwork to be saved.
     */
    void updateFavorite(Artwork artwork);

    void save(Artwork artwork) throws IOException;

    Artwork getArtworkById(String id) throws IOException;

    boolean contains(String id) throws IOException;

    List<Artwork> getAllFavorites();
}
