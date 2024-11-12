package use_case.click_art;

import entities.Artwork;

public class ClickArtInteractor implements ClickArtOutputBoundary{
    private final ClickArtDataAccessInterface clickArtAccessObject;
    private final ClickArtOutputBoundary clickArtPresenter;

    public ClickArtInteractor(ClickArtDataAccessInterface clickArtAccessObject,
                              ClickArtOutputBoundary clickArtPresenter) {
        this.clickArtAccessObject = clickArtAccessObject;
        this.clickArtPresenter = clickArtPresenter;
    }

    @Override
    public void execute(ClickArtInputData clickArtInputData) {
        // Fetch the artwork by its ID from the data access interface
        Artwork artwork = clickArtAccessObject.getArtworkById(clickArtInputData.getArtworkId());

        // Create output data
        ClickArtOutputData outputData = new ClickArtOutputData(artwork);

        // Pass the output data to the presenter
        clickArtPresenter.present(outputData);
    }

}
