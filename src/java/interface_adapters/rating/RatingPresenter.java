package interface_adapters.rating;
import interface_adapters.CFRViewModel;
import interface_adapters.ViewManagerModel;
import interface_adapters.click_art.ClickArtViewModel;
import interface_adapters.click_art.ClickArtPresenter;
import use_case.rating.RatingOutputBoundary;
import use_case.rating.RatingOutputData;
import view.CFRState;


public class RatingPresenter implements RatingOutputBoundary {
    private final CFRViewModel cfrViewModel;
    private final ViewManagerModel viewManagerModel;

    public RatingPresenter(CFRViewModel cfrViewModel, ViewManagerModel viewManagerModel) {
        this.cfrViewModel = cfrViewModel;
        this.viewManagerModel = viewManagerModel;

    }
    @Override
    public void prepareRatingView(RatingOutputData data) {
        final RatingState clickArtState = cfrViewModel.getRatingState();
    }
}
