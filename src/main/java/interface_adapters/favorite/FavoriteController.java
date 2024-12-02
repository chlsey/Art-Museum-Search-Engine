package interface_adapters.favorite;

import java.io.IOException;
import java.util.List;

import entities.Artwork;
import use_case.favorite.FavoriteInputBoundary;
import use_case.favorite.FavoriteInputData;

/**
 * Favorite controller class.
 */
public class FavoriteController {
    private final FavoriteInputBoundary favoriteUseCaseInteractor;

    public FavoriteController(FavoriteInputBoundary favoriteUseCaseInteractor) {
        this.favoriteUseCaseInteractor = favoriteUseCaseInteractor;
    }

    /**
     * Execute.
     * @param artwork artwork
     * @throws IOException exception
     */
    public void execute(Artwork artwork) throws IOException {
        final FavoriteInputData favoriteInputData = new FavoriteInputData(
                artwork.getId());

        favoriteUseCaseInteractor.execute(favoriteInputData);
    }

    /**
     * Go to favorite.
     */
    public void goToFavorite() {
        favoriteUseCaseInteractor.getFavoriteArtworks();
    }

    public List<Artwork> getFavorites() {
        return favoriteUseCaseInteractor.getFavorites();
    }
}
