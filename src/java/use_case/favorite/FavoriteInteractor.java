package use_case.favorite;

import data.FileArtworkDataAccessObject;
import data.InMemoryDataAccessObject;
import entities.Artwork;

public class FavoriteInteractor implements FavoriteInputBoundary {
    private final InMemoryDataAccessObject artworkDataAccessObject;
    private final FavoriteOutputBoundary artworkPresenter;


    public FavoriteInteractor(InMemoryDataAccessObject artworkDataAccessObject,
                              FavoriteOutputBoundary artworkPresenter) {
        this.artworkDataAccessObject = artworkDataAccessObject;
        this.artworkPresenter = artworkPresenter;
    }

    @Override
    public void execute(FavoriteInputData favoriteInputData) {
        if (artworkDataAccessObject.contains(favoriteInputData.getArtwork())) {
            artworkDataAccessObject.updateFavorite(favoriteInputData.getArtwork());
            Artwork art = favoriteInputData.getArtwork();
            art.setFavorited();
            artworkPresenter.prepareSuccessView(new FavoriteOutputData(art));
        } else {
            Artwork art = favoriteInputData.getArtwork();
            art.setFavorited();
            artworkDataAccessObject.save(art);
            artworkPresenter.prepareSuccessView(new FavoriteOutputData(art));
        }
    }

    @Override
    public void switchToArtworkView() {
    }
}
