package interface_adapters.search;

import use_case.search.SearchInputBoundary;
import use_case.search.SearchInputData;

/**
 * Controller for search use case.
 */

public class SearchController {
    private final SearchInputBoundary searchUseCaseInteractor;

    public SearchController(SearchInputBoundary searchUseCaseInteractor) {
        this.searchUseCaseInteractor = searchUseCaseInteractor;
    }

    /**
     * Executes the search Use Case.
     */
    public void execute(String searchMessage){
        final SearchInputData searchInputData = new SearchInputData(searchMessage);
        searchUseCaseInteractor.execute(searchInputData);
    }
}
