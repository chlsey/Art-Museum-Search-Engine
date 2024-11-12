package interface_adapters.click_art;

import use_case.click_art.ClickArtInputData;
import use_case.click_art.ClickArtOutputBoundary;
import use_case.click_art.ClickArtOutputData;
import interface_adapters.ViewManagerModel;
import interface_adapters.click_art.*;

public class ClickArtPresenter implements ClickArtOutputBoundary {
    private final ClickArtViewModel clickArtViewModel;
    private final ViewManagerModel viewManagerModel;

    public ClickArtPresenter(ClickArtViewModel clickArtViewModel,
                             ViewManagerModel viewManagerModel) {
        this.clickArtViewModel = clickArtViewModel;
        this.viewManagerModel = viewManagerModel;
    }


    @Override
    public void prepareSuccessView(ClickArtOutputData outputData) {
        clickArtViewModel.setArtwork(outputData.getArtwork());

        // Update the view state to display the detail view
        viewManagerModel.setSelectedArtwork(outputData.getArtwork());
        viewManagerModel.setState("ArtworkDetailView");
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final ClickArtState clickArtState = clickArtViewModel.getState();
        clickArtState.setClickArtError(error);
        clickArtViewModel.firePropertyChanged();
    }
}