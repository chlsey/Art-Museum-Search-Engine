package use_case.click_art;

public interface ClickArtInputBoundary {
    /**
     * Executes the login use case.
     * @param clickArtInputData the input data
     */
    void execute(ClickArtInputData clickArtInputData);
    void switchToSearchView();
}
