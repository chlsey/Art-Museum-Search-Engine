package use_case.favorite;

import java.io.IOException;
import java.util.List;

import entities.Artwork;

/**
 * Favorite input boundary.
 */
public interface FavoriteInputBoundary {
    /**
     * Execute.
     * @param favoriteInputData favoriteInputData
     * @throws IOException exception
     */
    void execute(FavoriteInputData favoriteInputData) throws IOException;

    /**
     * Get favorite artworks.
     */
    void getFavoriteArtworks();

    /**
     * Get favorite.
     * @return list of artwork
     */
    List<Artwork> getFavorites();
}
