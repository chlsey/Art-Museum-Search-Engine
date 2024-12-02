package use_case.click_art;

import data.MuseumDataAccessObject;
import entities.Artwork;

/**
 * Click art interactor.
 */
public class ClickArtInteractor implements ClickArtInputBoundary {
    private final MuseumDataAccessObject artworkDataAccessObject;
    private final ClickArtOutputBoundary clickArtPresenter;

    public ClickArtInteractor(MuseumDataAccessObject artworkDataAccessObject,
                              ClickArtOutputBoundary clickArtOutputBoundary) {
        this.artworkDataAccessObject = artworkDataAccessObject;
        this.clickArtPresenter = clickArtOutputBoundary;
    }

    @Override
    public void execute(ClickArtInputData clickArtInputData) {
        final Artwork artwork = clickArtInputData.getArtwork();
        final String url = artwork.getImageUrl();
        final String title = artwork.getTitle();
        final String artistName = artwork.getArtistName();
    }

    @Override
    public void switchToSearchView() {
        clickArtPresenter.switchToSearchView();
    }

    /**
     * Switch to cfr view.
     * @param clickArtInputData clickArtInputData
     */
    public void switchToCFRView(ClickArtInputData clickArtInputData) {
        final ClickArtOutputData clickArtOutputData = new ClickArtOutputData(clickArtInputData.getArtwork());
        clickArtPresenter.switchToCFRView(clickArtOutputData);
    }
}
