package use_case.click_art;

import entities.Artwork;

/**
 * Click art input data.
 */
public class ClickArtInputData {
    private Artwork artwork;

    public ClickArtInputData(Artwork artwork) {
        this.artwork = artwork;
    }

    public Artwork getArtwork() {
        return artwork;
    }
}
