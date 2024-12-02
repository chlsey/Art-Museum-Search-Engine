package use_case.favorite;

import java.util.List;

import entities.Artwork;

/**
 * Favorite output data.
 */
public class FavoriteOutputData {
    private final List<Artwork> favoritedArtworks;
    /**
     * Favorite ouput data.
     * @param favoritedArtworks favoritedArtworks
     */
    public FavoriteOutputData(List<Artwork> favoritedArtworks) {
        this.favoritedArtworks = favoritedArtworks;
    }

    /**
     * Get favorite artworks.
     * @return list artwork
     */
    public List<Artwork> getFavoriteArtworks() {
        return favoritedArtworks;
    }
}
