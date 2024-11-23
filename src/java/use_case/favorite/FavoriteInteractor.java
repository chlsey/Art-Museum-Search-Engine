package use_case.favorite;

import data.InMemoryDataAccessObject;
import entities.Artwork;

public class FavoriteInteractor implements FavoriteInputBoundary {
    private final FavoriteDataAccessInterface artworkDataAccessObject;
    private final FavoriteOutputBoundary artworkPresenter;


    public FavoriteInteractor(FavoriteDataAccessInterface artworkDataAccessObject,
                              FavoriteOutputBoundary artworkPresenter) {
        this.artworkDataAccessObject = artworkDataAccessObject;
        this.artworkPresenter = artworkPresenter;
    }

    @Override
    public void execute(FavoriteInputData favoriteInputData) {
        if (artworkDataAccessObject.contains(favoriteInputData.getArtwork())) {
            artworkDataAccessObject.updateFavorite(favoriteInputData.getArtwork());
            Artwork art = favoriteInputData.getArtwork();
            artworkPresenter.prepareSuccessView(new FavoriteOutputData(art));
        } else {
            Artwork art = favoriteInputData.getArtwork();
            artworkDataAccessObject.save(art);
            artworkDataAccessObject.updateFavorite(art);
            artworkPresenter.prepareSuccessView(new FavoriteOutputData(art));
        }
    }

}
