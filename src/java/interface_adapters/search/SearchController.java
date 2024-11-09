package java.interface_adapters.search;

import java.use_case.search.SearchInputBoundary;
import java.use_case.search.SearchInputData;

/**
 * Controller for artwork search use case.
 */

public class SearchController {
    private final SearchInputBoundary SearchInputBoundary;

    public SearchController(SearchInputBoundary artworkSearchInputBoundary) {
        this.SearchInputBoundary = artworkSearchInputBoundary;
    }

    /**
     * Executes the artwork search Use Case.
     */
    public void execute(String searchMessage){
        final SearchInputData artworkSearchInputData = new SearchInputData(searchMessage);
    }
}
