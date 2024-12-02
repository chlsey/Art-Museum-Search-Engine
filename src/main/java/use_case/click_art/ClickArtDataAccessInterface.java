package use_case.click_art;

import java.io.IOException;

import entities.Artwork;

/**
 * Click art data access Interface.
 */
public interface ClickArtDataAccessInterface {
    /**
     * Get selected Artwork.
     * @param artwork Artwork
     * @return The selected Artwork.
     * @throws IOException an exception.
     */
    Artwork getSelectedArtwork(Artwork artwork) throws IOException;
}
