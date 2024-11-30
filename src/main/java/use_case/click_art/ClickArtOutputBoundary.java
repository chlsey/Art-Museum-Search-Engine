package use_case.click_art;

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

    void switchToSearchView();

    void switchToCFRView(ClickArtOutputData clickArtOutputData);
}
