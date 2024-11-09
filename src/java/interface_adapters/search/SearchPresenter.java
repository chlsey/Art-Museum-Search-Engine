package interface_adapters.search;

import use_case.search.SearchOutputBoundary;
import use_case.search.SearchOutputData;

public class SearchPresenter extends SearchOutputBoundary {

    private final SearchViewModel searchViewModel;

    public SearchPresenter(SearchViewModel searchViewModel) {
        this.searchViewModel = searchViewModel;
    }



}
