package interface_adapters.search;

import use_case.search.*;

/**
 * Controller for artwork search use case.
 */
public class SearchController {
    private final SearchInputBoundary artworkSearchInputBoundary;

    public SearchController(SearchInputBoundary artworkSearchInputBoundary) {
        this.artworkSearchInputBoundary = artworkSearchInputBoundary;
    }

    public void execute(String searchMessage, String filter){
        final SearchInputData artworkSearchInputData = new SearchInputData(searchMessage, filter);
    }
}