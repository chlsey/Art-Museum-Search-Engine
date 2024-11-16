package interface_adapters.favorite;

import use_case.favorite.FavoriteInputBoundary;

public class FavoriteController {
    private final FavoriteInputBoundary favoriteUseCaseInteractor;

    public FavoriteController(FavoriteInputBoundary favoriteUseCaseInteractor) {
        this.favoriteUseCaseInteractor = favoriteUseCaseInteractor;
    }
}
