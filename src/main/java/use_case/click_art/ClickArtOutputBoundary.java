package use_case.click_art;

/**
 * Click art output boundary.
 */
public interface ClickArtOutputBoundary {
    /**
     * Prepares the success view for the Login Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(ClickArtOutputData outputData);

    /**
     * Prepares the failure view for the Login Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);

    /**
     * Switch to search view.
     */
    void switchToSearchView();

    /**
     * Switch to cfr view.
     * @param clickArtOutputData clickArtOutputData
     */
    void switchToCFRView(ClickArtOutputData clickArtOutputData);
}
