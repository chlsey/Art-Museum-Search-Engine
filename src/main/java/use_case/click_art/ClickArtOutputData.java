package use_case.click_art;

import entities.Artwork;

/**
 * Click art output data.
 */
public class ClickArtOutputData {
    private Artwork artwork;

    public ClickArtOutputData(Artwork artwork) {
        this.artwork = artwork;

    }

    public String getArtUrl() {
        return artwork.getImageUrl();
    }

    public String getTitle() {
        return this.artwork.getTitle();
    }

    public String getDescription() {
        return this.artwork.getDescription();
    }

    public String getArtistName() {
        return this.artwork.getArtistName();
    }

    public String getTimePeriod() {
        return this.artwork.getTimePeriod();
    }
}
