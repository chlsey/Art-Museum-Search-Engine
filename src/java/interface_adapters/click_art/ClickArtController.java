package interface_adapters.click_art;

import use_case.click_art.ClickArtInputBoundary;
import use_case.click_art.ClickArtInputData;
import entities.Artwork;

public class ClickArtController {
    private final ClickArtInputBoundary ClickArtUseCaseInteractor;

    public ClickArtController(ClickArtInputBoundary ClickArtUseCaseInteractor) {
        this.ClickArtUseCaseInteractor = ClickArtUseCaseInteractor;
    }

    public void execute(Artwork artwork) {
        final ClickArtInputData clickArtInputData = new ClickArtInputData(artwork);
        ClickArtUseCaseInteractor.execute(clickArtInputData);
    }
}

