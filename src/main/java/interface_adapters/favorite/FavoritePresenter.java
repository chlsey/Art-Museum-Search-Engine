package interface_adapters.favorite;

import entities.Artwork;
import interface_adapters.CFRViewModel;
import interface_adapters.ViewManagerModel;
import interface_adapters.click_art.ClickArtState;
import interface_adapters.click_art.ClickArtViewModel;
import interface_adapters.search.SearchViewModel;
import use_case.favorite.FavoriteOutputBoundary;
import use_case.favorite.FavoriteOutputData;

import java.util.List;

public class FavoritePresenter implements FavoriteOutputBoundary {

    private final CFRViewModel cfrViewModel;
    private final ViewManagerModel viewManagerModel;
    private final ClickArtViewModel clickArtViewModel;
    private final SearchViewModel searchViewModel;

    public FavoritePresenter(CFRViewModel cfrViewModel, ViewManagerModel viewManagerModel, ClickArtViewModel clickArtViewModel, SearchViewModel searchViewModel) {
        this.cfrViewModel = cfrViewModel;
        this.viewManagerModel = viewManagerModel;
        this.clickArtViewModel = clickArtViewModel;
        this.searchViewModel = searchViewModel;
    }

    @Override
    public void getAllFavorites(FavoriteOutputData outputData) {
        List<Artwork> favoriteArtworks = outputData.getFavoriteArtworks();
        final ClickArtState clickArtState = clickArtViewModel.getState();
        clickArtState.setArtworks(favoriteArtworks);
        this.clickArtViewModel.setState(clickArtState);
        this.clickArtViewModel.firePropertyChanged();
        searchViewModel.setArtworks(favoriteArtworks);
        //searchViewModel.firePropertyChanged();

        this.viewManagerModel.setState(clickArtViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged("searched");
    }


//    @Override
//    public void prepareSuccessView(FavoriteOutputData outputData) {
//        final FavoriteState favoriteState = cfrViewModel.getFavoriteState();
//        favoriteState.setFavorite();
//        this.viewManagerModel.setState(cfrViewModel.getViewName());
//        cfrViewModel.firePropertyChanged();
//    }
//
//    @Override
//    public void prepareFailView(String errorMessage) {
//
//    }
}
