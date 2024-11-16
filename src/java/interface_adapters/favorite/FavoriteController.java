package interface_adapters.favorite;

import entities.Artwork;
import use_case.favorite.FavoriteInputBoundary;
import use_case.favorite.FavoriteInputData;

public class FavoriteController {
    private final FavoriteInputBoundary favoriteUseCaseInteractor;

    public FavoriteController(FavoriteInputBoundary favoriteUseCaseInteractor) {
        this.favoriteUseCaseInteractor = favoriteUseCaseInteractor;
    }

    public void execute(Artwork artwork) {
        final FavoriteInputData favoriteInputData = new FavoriteInputData(
                artwork);

        favoriteUseCaseInteractor.execute(favoriteInputData);
    }

}
