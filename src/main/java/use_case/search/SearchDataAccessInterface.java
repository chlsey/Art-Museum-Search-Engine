package use_case.search;

import java.util.List;

import entities.Artwork;

/**
 * Data access interface for search.
 */
public interface SearchDataAccessInterface {
    /**
     * Search artwork based on the keyword.
     * @param searchMessage String
     * @return a list of related artworks
     */
    List<Artwork> searchArtwork(String searchMessage);
}
