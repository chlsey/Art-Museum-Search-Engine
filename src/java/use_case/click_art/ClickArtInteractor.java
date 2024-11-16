package use_case.click_art;


import entities.Artwork;

public class ClickArtInteractor implements ClickArtInputBoundary {
    private final ClickArtDataAccessInterface clickArtAccessObject;
    private final ClickArtOutputBoundary clickArtPresenter;

    public ClickArtInteractor(ClickArtDataAccessInterface userDataAccessInterface,
                                  ClickArtOutputBoundary clickArtOutputBoundary) {
        this.clickArtAccessObject = userDataAccessInterface;
        this.clickArtPresenter = clickArtOutputBoundary;
    }


    @Override
    public void execute(ClickArtInputData clickArtInputData) {
        final Artwork artwork = clickArtInputData.getArtwork();
        String url = artwork.getImageUrl();
        String title = artwork.getTitle();
        String description = artwork.getDescription();
        String artistName = artwork.getArtistName();
        String timePeriod = artwork.getTimePeriod();
        final ClickArtOutputData clickArtOutputData = new ClickArtOutputData(url, title,
                description, artistName, timePeriod);
        clickArtPresenter.prepareSuccessView(clickArtOutputData);
//        clickArtPresenter.prepareFailView("Page not found.");

    }
}
