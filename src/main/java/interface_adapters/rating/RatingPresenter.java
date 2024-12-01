package interface_adapters.rating;

import interface_adapters.CfrViewModel;
import interface_adapters.ViewManagerModel;
import interface_adapters.click_art.ClickArtViewModel;
import interface_adapters.search.SearchViewModel;
import use_case.rating.RatingOutputBoundary;
import use_case.rating.RatingOutputData;

/**
 * Rating Presenter class.
 */
public class RatingPresenter implements RatingOutputBoundary {

    private final CfrViewModel cfrViewModel;
    private final ViewManagerModel viewManagerModel;
    private final ClickArtViewModel clickArtViewModel;
    private final SearchViewModel searchViewModel;

    public RatingPresenter(CfrViewModel cfrViewModel, ViewManagerModel viewManagerModel,
                           ClickArtViewModel clickArtViewModel, SearchViewModel searchViewModel) {
        this.cfrViewModel = cfrViewModel;
        this.viewManagerModel = viewManagerModel;
        this.clickArtViewModel = clickArtViewModel;
        this.searchViewModel = searchViewModel;
    }

    @Override
    public void prepareSuccessView(RatingOutputData outputData) {
        cfrViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailureView(String errorMessage) {

    }
}
