package interface_adapters.rating;

import interface_adapters.CFRViewModel;
import interface_adapters.ViewManagerModel;
import interface_adapters.click_art.ClickArtViewModel;
import interface_adapters.search.SearchViewModel;
import use_case.rating.RatingOutputBoundary;
import use_case.rating.RatingOutputData;

public class RatingPresenter implements RatingOutputBoundary {

    private final CFRViewModel cfrViewModel;
    private final ViewManagerModel viewManagerModel;
    private final ClickArtViewModel clickArtViewModel;
    private final SearchViewModel searchViewModel;

    public RatingPresenter(CFRViewModel cfrViewModel, ViewManagerModel viewManagerModel,
                           ClickArtViewModel clickArtViewModel, SearchViewModel searchViewModel) {
        this.cfrViewModel = cfrViewModel;
        this.viewManagerModel = viewManagerModel;
        this.clickArtViewModel = clickArtViewModel;
        this.searchViewModel = searchViewModel;
    }

    @Override
    public void prepareSuccessView(RatingOutputData outputData) {
//        final RatingState ratingState = cfrViewModel.getRatingState();
//        ratingState.setRating(outputData.getRating());
//        this.viewManagerModel.setState(cfrViewModel.getViewName());
        cfrViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailureView(String errorMessage) {

    }
}
