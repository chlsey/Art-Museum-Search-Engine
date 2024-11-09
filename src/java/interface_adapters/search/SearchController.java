package interface_adapters.search;

import use_case.artwork_search.ArtworkSearchInputBoundary;
import use_case.artwork_search.ArtworkSearchInputData;

/**
 * Controller for artwork search use case.
 */

public class SearchController {
    private final ArtworkSearchInputBoundary artworkSearchInputBoundary;

    public SearchController(ArtworkSearchInputBoundary artworkSearchInputBoundary) {
        this.artworkSearchInputBoundary = artworkSearchInputBoundary;
    }

    /**
     * Executes the artwork search Use Case.
     */
    public void execute(String searchMessage){
        final ArtworkSearchInputData artworkSearchInputData = new ArtworkSearchInputData(searchMessage);
    }
}
