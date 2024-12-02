package interface_adapters.filter;

import interface_adapters.ViewManagerModel;
import interface_adapters.click_art.ClickArtViewModel;
import interface_adapters.search.SearchViewModel;
import use_case.filter.FilterOutputBoundary;
import use_case.filter.FilterOutputData;

/**
 * Filter presenter class.
 */
public class FilterPresenter implements FilterOutputBoundary {

    private final SearchViewModel searchViewModel;
    private final ViewManagerModel viewManagerModel;
    private final ClickArtViewModel clickArtViewModel;

    public FilterPresenter(SearchViewModel searchViewModel, ViewManagerModel viewManagerModel,
                           ClickArtViewModel clickArtViewModel) {
        this.searchViewModel = searchViewModel;
        this.viewManagerModel = viewManagerModel;
        this.clickArtViewModel = clickArtViewModel;
    }

    @Override
    public void setFilter(FilterOutputData currentFilter) {
    }
}
