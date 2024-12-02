package interface_adapters.click_art;

import interface_adapters.CfrViewModel;
import interface_adapters.ViewManagerModel;
import interface_adapters.search.SearchViewModel;
import use_case.click_art.ClickArtOutputBoundary;
import use_case.click_art.ClickArtOutputData;

/**
 * Click art presenter class.
 */
public class ClickArtPresenter implements ClickArtOutputBoundary {
    private final SearchViewModel searchViewModel;
    private final ClickArtViewModel clickArtViewModel;
    private final ViewManagerModel viewManagerModel;
    private final CfrViewModel cfrViewModel;

    /**
     * Click art presenter.
     * @param searchViewModel searchViewModel
     * @param clickArtViewModel clickArtViewModel
     * @param cfrViewModel cfrViewModel
     * @param viewManagerModel viewManagerModel
     */
    public ClickArtPresenter(SearchViewModel searchViewModel, ClickArtViewModel clickArtViewModel,
                             CfrViewModel cfrViewModel,
                             ViewManagerModel viewManagerModel) {
        this.searchViewModel = searchViewModel;
        this.clickArtViewModel = clickArtViewModel;
        this.viewManagerModel = viewManagerModel;
        this.cfrViewModel = cfrViewModel;
    }

    /**
     * Prepare success view.
     * @param artWork the output data
     */
    @Override
    public void prepareSuccessView(ClickArtOutputData artWork) {
        final ClickArtState clickArtState = clickArtViewModel.getState();
        clickArtState.setArtUrl(artWork.getArtUrl());
        clickArtState.setTitle(artWork.getTitle());
        clickArtState.setDescription(artWork.getDescription());
        clickArtState.setArtistName(artWork.getArtistName());
        clickArtState.setTimePeriod(artWork.getTimePeriod());

        // Update the view state to display the detail view
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final ClickArtState clickArtState = clickArtViewModel.getState();
        clickArtState.setClickArtError(error);
        clickArtViewModel.firePropertyChanged();
    }

    /**
     * Switch to search view.
     */
    public void switchToSearchView() {
        viewManagerModel.setState(searchViewModel.getViewName());
        viewManagerModel.firePropertyChanged("search");
    }

    /**
     * Switch to crf view.
     * @param clickArtOutputData clickArtOutputData
     */
    public void switchToCFRView(ClickArtOutputData clickArtOutputData) {
        this.cfrViewModel.setState(cfrViewModel.getState());
        this.cfrViewModel.firePropertyChanged("CFRView");
        viewManagerModel.setState(cfrViewModel.getViewName());
        viewManagerModel.firePropertyChanged("CFRView");
        cfrViewModel.setSelectedArtwork(clickArtViewModel.getSelectedArtwork());
    }
}
