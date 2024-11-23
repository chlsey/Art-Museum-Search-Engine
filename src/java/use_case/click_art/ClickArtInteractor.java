package use_case.click_art;


import data.InMemoryDataAccessObject;
import data.MuseumDataAccessObject;
import entities.Artwork;
import use_case.comment.CommentDataAccessInterface;

public class ClickArtInteractor implements ClickArtInputBoundary {
    private final MuseumDataAccessObject artworkDataAccessObject;
    private final ClickArtOutputBoundary clickArtPresenter;

    public ClickArtInteractor(MuseumDataAccessObject artworkDataAccessObject,
                              ClickArtOutputBoundary clickArtOutputBoundary) {
        this.artworkDataAccessObject = artworkDataAccessObject;
        this.clickArtPresenter = clickArtOutputBoundary;
    }


    @Override
    public void execute(ClickArtInputData clickArtInputData) {
        final Artwork artwork = clickArtInputData.getArtwork();
        String url = artwork.getImageUrl();
        String title = artwork.getTitle();
//        String description = artwork.getDescription();
        String artistName = artwork.getArtistName();
//        String timePeriod = artwork.getTimePeriod();
//        final ClickArtOutputData clickArtOutputData = new ClickArtOutputData(url, title,
//                description, artistName, timePeriod);
//        clickArtPresenter.prepareSuccessView(clickArtOutputData);
//        clickArtPresenter.prepareFailView("Page not found.");

    }

    @Override
    public void switchToSearchView() {
        clickArtPresenter.switchToSearchView();
    }
}
