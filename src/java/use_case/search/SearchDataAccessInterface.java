package use_case.search;

import entities.Artwork;
import java.util.ArrayList;

public interface SearchDataAccessInterface {
    /**
     * Queries the DB to find a list of artworks that match the search message.
     * @param searchMessage the list of words used to find artworks.
     */
    ArrayList<Artwork> searchArtworks(String searchMessage);
}
