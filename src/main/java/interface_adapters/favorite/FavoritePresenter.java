package interface_adapters.favorite;

import java.util.List;

import entities.Artwork;
import interface_adapters.CfrViewModel;
import interface_adapters.ViewManagerModel;
import interface_adapters.click_art.ClickArtState;
import interface_adapters.click_art.ClickArtViewModel;
import interface_adapters.search.SearchViewModel;
import use_case.favorite.FavoriteOutputBoundary;
import use_case.favorite.FavoriteOutputData;

/**
 * Favorite Presenter Class.
 */
public class FavoritePresenter implements FavoriteOutputBoundary {

    private final CfrViewModel cfrViewModel;
    private final ViewManagerModel viewManagerModel;
    private final ClickArtViewModel clickArtViewModel;
    private final SearchViewModel searchViewModel;

    public FavoritePresenter(CfrViewModel cfrViewModel, ViewManagerModel viewManagerModel,
                             ClickArtViewModel clickArtViewModel, SearchViewModel searchViewModel) {
        this.cfrViewModel = cfrViewModel;
        this.viewManagerModel = viewManagerModel;
        this.clickArtViewModel = clickArtViewModel;
        this.searchViewModel = searchViewModel;
    }

    @Override
    public void getAllFavorites(FavoriteOutputData outputData) {
        final List<Artwork> favoriteArtworks = outputData.getFavoriteArtworks();
        final ClickArtState clickArtState = clickArtViewModel.getState();
        clickArtState.setArtworks(favoriteArtworks);
        this.clickArtViewModel.setState(clickArtState);
        this.clickArtViewModel.firePropertyChanged();
        searchViewModel.setArtworks(favoriteArtworks);
        this.viewManagerModel.setState(clickArtViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged("searched");
    }
}
