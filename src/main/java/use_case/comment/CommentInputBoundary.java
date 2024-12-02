package use_case.comment;

import java.io.IOException;
import java.util.List;

import entities.Artwork;

/**
 * Comment input boundary.
 */
public interface CommentInputBoundary {
    /**
     * Add comment.
     * @param commentInputData commentInputData
     * @throws IOException exception
     */
    void execute(CommentInputData commentInputData) throws IOException;

    /**
     * Get comment artwork.
     * @return list of artwork
     */
    List<Artwork> getCommentedArtworks();
}
