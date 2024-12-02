package interface_adapters.search;

import use_case.search.SearchInputBoundary;
import use_case.search.SearchInputData;

/**
 * Controller for artwork search use case.
 */
public class SearchController {
    private final SearchInputBoundary artworkSearchInputBoundary;

    public SearchController(SearchInputBoundary artworkSearchInputBoundary) {
        this.artworkSearchInputBoundary = artworkSearchInputBoundary;
    }

    /**
     * Execute.
     * @param keyword keyword
     */
    public void execute(String keyword) {
        final SearchInputData artworkSearchInputData = new SearchInputData(keyword);

        artworkSearchInputBoundary.execute(artworkSearchInputData);
    }
}
