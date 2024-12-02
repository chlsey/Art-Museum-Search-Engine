package use_case.comment;

import java.io.IOException;
import java.util.List;

import entities.Artwork;

/**
 * Comment data access interface.
 */
public interface CommentDataAccessInterface {
    /**
     * Add comment to artwork.
     * @param artwork artwork
     * @param comment comment
     * @throws IOException exception
     */
    void addCommentToArtwork(Artwork artwork, String comment) throws IOException;

    /**
     * Get artwork by id.
     * @param id id
     * @return exception
     * @throws IOException exception
     */
    Artwork getArtworkById(String id) throws IOException;

    /**
     * Get comment artwork.
     * @return list of artwork
     */
    List<Artwork> getCommentedArtworks();
}
