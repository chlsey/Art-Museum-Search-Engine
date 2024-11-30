package interface_adapters.favorite;

import interface_adapters.CFRViewModel;
import interface_adapters.ViewManagerModel;
import use_case.favorite.FavoriteOutputBoundary;
import use_case.favorite.FavoriteOutputData;

public class FavoritePresenter implements FavoriteOutputBoundary {

    private final CFRViewModel cfrViewModel;
    private final ViewManagerModel viewManagerModel;

    public FavoritePresenter(CFRViewModel cfrViewModel, ViewManagerModel viewManagerModel) {
        this.cfrViewModel = cfrViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(FavoriteOutputData outputData) {
        final FavoriteState favoriteState = cfrViewModel.getFavoriteState();
        favoriteState.setFavorite();
        this.viewManagerModel.setState(cfrViewModel.getViewName());
        cfrViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {

    }
}
