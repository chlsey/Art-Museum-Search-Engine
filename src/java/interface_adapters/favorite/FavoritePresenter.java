package interface_adapters.favorite;

import interface_adapters.ViewManagerModel;
import use_case.favorite.FavoriteOutputBoundary;
import use_case.favorite.FavoriteOutputData;

public class FavoritePresenter implements FavoriteOutputBoundary {

    private final FavoriteViewModel favoriteViewModel;
    private final ViewManagerModel viewManagerModel;

    public FavoritePresenter(FavoriteViewModel favoriteViewModel, ViewManagerModel viewManagerModel) {
        this.favoriteViewModel = favoriteViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(FavoriteOutputData outputData) {

    }

    @Override
    public void prepareFailView(String errorMessage) {

    }
}
