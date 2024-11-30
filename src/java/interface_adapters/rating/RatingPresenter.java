package interface_adapters.rating;

import interface_adapters.CFRViewModel;
import interface_adapters.ViewManagerModel;
import interface_adapters.rating.RatingState;
import use_case.rating.RatingOutputBoundary;
import use_case.rating.RatingOutputData;

public class RatingPresenter implements RatingOutputBoundary {

    private final CFRViewModel cfrViewModel;
    private final ViewManagerModel viewManagerModel;

    public RatingPresenter(CFRViewModel cfrViewModel, ViewManagerModel viewManagerModel) {
        this.cfrViewModel = cfrViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(RatingOutputData outputData) {
        final RatingState ratingState = cfrViewModel.getRatingState();
        ratingState.setRating(outputData.getRating());
        this.viewManagerModel.setState(cfrViewModel.getViewName());
        cfrViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailureView(String errorMessage) {

    }
}
