package use_case.search;

import entities.Artwork;

import java.util.ArrayList;
import java.util.List;

public interface SearchDataAccessInterface {
    /**
     * Queries the DB to find a list of artworks that match the search message.
     * @param searchMessage the list of words used to find artworks.
     */
    List<Artwork> searchArtworks(String searchMessage);
}
