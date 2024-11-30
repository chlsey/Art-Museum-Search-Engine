package interface_adapters.click_art;

import entities.Artwork;
import interface_adapters.CFRViewModel;
import interface_adapters.search.SearchViewModel;
import use_case.click_art.ClickArtOutputBoundary;
import use_case.click_art.ClickArtOutputData;
import interface_adapters.ViewManagerModel;

import java.util.List;

public class ClickArtPresenter implements ClickArtOutputBoundary {
    private final SearchViewModel searchViewModel;
    private final ClickArtViewModel clickArtViewModel;
    private final ViewManagerModel viewManagerModel;
    private final CFRViewModel cfrViewModel;

    public ClickArtPresenter(SearchViewModel searchViewModel, ClickArtViewModel clickArtViewModel, CFRViewModel cfrViewModel,
                             ViewManagerModel viewManagerModel) {
        this.searchViewModel = searchViewModel;
        this.clickArtViewModel = clickArtViewModel;
        this.viewManagerModel = viewManagerModel;
        this.cfrViewModel = cfrViewModel;
    }


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

    public void switchToSearchView(){
        viewManagerModel.setState(searchViewModel.getViewName());
        viewManagerModel.firePropertyChanged("search");
    }

    public void switchToCFRView(ClickArtOutputData clickArtOutputData){
        //System.out.println(cfrViewModel.getViewName());
        this.cfrViewModel.setState(cfrViewModel.getState());
        this.cfrViewModel.firePropertyChanged("CFRView");
        viewManagerModel.setState(cfrViewModel.getViewName());
        viewManagerModel.firePropertyChanged("CFRView");
        //System.out.println(viewManagerModel.getState());
        cfrViewModel.setSelectedArtwork(clickArtViewModel.getSelectedArtwork());
        //System.out.println(cfrViewModel.getSelectedArtwork().getRating());

    }
}