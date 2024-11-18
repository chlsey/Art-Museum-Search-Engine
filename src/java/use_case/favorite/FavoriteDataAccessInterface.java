package use_case.favorite;

import entities.Artwork;

public interface FavoriteDataAccessInterface {
    /**
     * Queries the DB to find a list of artworks that match the search message.
     * @param artwork the artwork to be saved.
     */
    void updateFavorite(Artwork artwork);

    void save(Artwork artwork);

    Artwork getArtworkByTitle(String title);

    boolean contains(Artwork artwork);
}
