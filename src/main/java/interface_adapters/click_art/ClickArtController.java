package interface_adapters.click_art;

import java.io.IOException;

import entities.Artwork;
import use_case.click_art.ClickArtInputBoundary;
import use_case.click_art.ClickArtInputData;

/**
 * Click art controller class.
 */
public class ClickArtController {
    private final ClickArtInputBoundary clickArtUseCaseInteractor;

    /**
     * Click art controller.
     * @param ClickArtUseCaseInteractor ClickArtUseCaseInteractor
     */
    public ClickArtController(ClickArtInputBoundary ClickArtUseCaseInteractor) {
        this.clickArtUseCaseInteractor = ClickArtUseCaseInteractor;
    }

    /**
     * Exectue.
     * @param artwork artwork
     * @throws IOException an exception
     */
    public void execute(Artwork artwork) throws IOException {
        final ClickArtInputData clickArtInputData = new ClickArtInputData(artwork);
        clickArtUseCaseInteractor.execute(clickArtInputData);
    }

    /**
     * Switch to search.
     */
    public void switchToSearch() {
        clickArtUseCaseInteractor.switchToSearchView();
    }

    /**
     * Switch to cfr.
     * @param artwork the arwork
     */
    public void switchToCFR(Artwork artwork) {
        clickArtUseCaseInteractor.switchToCFRView(new ClickArtInputData(artwork));
    }
}
