package use_case.comment;

import java.io.IOException;
import java.util.List;

import entities.Artwork;

/**
 * Comment Data Access Interface.
 */
public interface CommentDataAccessInterface {
    /**
     * Add a comment.
     * @param artwork Artwork
     * @param comment String
     * @throws IOException String
     */
    void addCommentToArtwork(Artwork artwork, String comment) throws IOException;

    /**
     * Get artwork by its id.
     * @param id String
     * @return the artwork with the same id.
     * @throws IOException string
     */
    Artwork getArtworkById(String id) throws IOException;

    /**
     * Get all commented artworks.
     * @return a list of commented artworks.
     */
    List<Artwork> getCommentedArtworks();
}
