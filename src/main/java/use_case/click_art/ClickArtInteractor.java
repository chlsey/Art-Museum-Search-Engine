package use_case.click_art;

import java.io.IOException;

import data.MuseumDataAccessObject;
import entities.Artwork;

/**
 * Click art interactor.
 */
public class ClickArtInteractor implements ClickArtInputBoundary {
    private final ClickArtDataAccessInterface clickDataAccessObject;
    private final ClickArtOutputBoundary clickArtPresenter;

    public ClickArtInteractor(MuseumDataAccessObject artworkDataAccessObject,
                              ClickArtOutputBoundary clickArtOutputBoundary) {
        this.clickDataAccessObject = artworkDataAccessObject;
        this.clickArtPresenter = clickArtOutputBoundary;
    }

    @Override
    public void execute(ClickArtInputData clickArtInputData) throws IOException {
        final Artwork artwork = clickArtInputData.getArtwork();
        clickDataAccessObject.getSelectedArtwork(artwork);
        final ClickArtOutputData clickArtOutputData = new ClickArtOutputData(clickArtInputData.getArtwork());
        clickArtPresenter.switchToCFRView(clickArtOutputData);
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
