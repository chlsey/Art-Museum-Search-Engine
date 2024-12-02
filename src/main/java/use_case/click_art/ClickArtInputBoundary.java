package use_case.click_art;

/**
 * Click art input boundary.
 */
public interface ClickArtInputBoundary {
    /**
     * Executes the login use case.
     * @param clickArtInputData the input data
     */
    void execute(ClickArtInputData clickArtInputData);

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
