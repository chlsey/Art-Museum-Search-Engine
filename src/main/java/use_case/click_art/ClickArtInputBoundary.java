package use_case.click_art;

import java.io.IOException;

/**
 * Click art input boundary.
 */
public interface ClickArtInputBoundary {
    /**
     * Executes the login use case.
     * @param clickArtInputData the input data
     * @throws IOException An exception
     */
    void execute(ClickArtInputData clickArtInputData) throws IOException;

    /**
     * Switch to search view.
     */
    void switchToSearchView();

    /**
     * Switch to cfr view.
     * @param clickArtInputData clickArtInputData
     */
    void switchToCFRView(ClickArtInputData clickArtInputData);
}
