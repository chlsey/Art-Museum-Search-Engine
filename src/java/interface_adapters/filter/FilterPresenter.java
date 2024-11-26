package interface_adapters.filter;

import entities.Artwork;
import interface_adapters.ViewManagerModel;
import interface_adapters.click_art.ClickArtState;
import interface_adapters.click_art.ClickArtViewModel;
import interface_adapters.search.SearchViewModel;
import use_case.filter.FilterOutputBoundary;
import use_case.filter.FilterOutputData;

import java.util.List;

public class FilterPresenter implements FilterOutputBoundary {

    private final SearchViewModel searchViewModel;
    private final ViewManagerModel viewManagerModel;
    private final ClickArtViewModel clickArtViewModel;

    public FilterPresenter(SearchViewModel searchViewModel, ViewManagerModel viewManagerModel, ClickArtViewModel clickArtViewModel) {
        this.searchViewModel = searchViewModel;
        this.viewManagerModel = viewManagerModel;
        this.clickArtViewModel = clickArtViewModel;
    }

    @Override
    public void prepareFilter(FilterOutputData currentfilter) {
        String filter = currentfilter.getFilter();


    }

//    @Override
//    public void prepareFailView(String errorMessage) {
//        final FilterState searchState = FilterViewModel.getState();
//        FilterViewModel.firePropertyChanged();
//    }

//    public void prepareSuccessView(FilterOutputData filterOutputData) {
//        String spec = filterOutputData.getFilter();
//        final ClickArtState clickArtState = clickArtViewModel.getState();
//        clickArtState.setArtworks(ourArtworks);
//        this.clickArtViewModel.setState(clickArtState);
//        this.clickArtViewModel.firePropertyChanged();
//        searchViewModel.setArtworks(ourArtworks);
//        //searchViewModel.firePropertyChanged();
//
//        this.viewManagerModel.setState(clickArtViewModel.getViewName());
//        this.viewManagerModel.firePropertyChanged("searched");
//
//    }
}
