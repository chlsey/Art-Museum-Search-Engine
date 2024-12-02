package use_case.favorite;

/**
 * Favorite output boundary.
 */
public interface FavoriteOutputBoundary {
    /**
     * Get all favorite.
     * @param outputData outputData
     */
    void getAllFavorites(FavoriteOutputData outputData);
}
