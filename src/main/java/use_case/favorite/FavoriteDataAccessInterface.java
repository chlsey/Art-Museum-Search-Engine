package use_case.favorite;

import java.io.IOException;
import java.util.List;

import entities.Artwork;

/**
 * Favorite data access interface.
 */
public interface FavoriteDataAccessInterface {
    /**
     * Queries the DB to find a list of artworks that match the search message.
     * @param id is the id of the artwork to be saved.
     */
    void updateFavorite(String id);

    /**
     * Save.
     * @param artwork artwork
     * @throws IOException exception
     */
    void save(Artwork artwork) throws IOException;

    /**
     * Get artwork by id.
     * @param id id
     * @return exception
     * @throws IOException exception
     */
    Artwork getArtworkById(String id) throws IOException;

    /**
     * Contains.
     * @param id id
     * @return exception
     * @throws IOException exception
     */
    boolean contains(String id) throws IOException;

    /**
     * Get all favorites.
     * @return list of artwork
     */
    List<Artwork> getAllFavorites();
}
