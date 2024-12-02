package use_case.comment;

import java.io.IOException;
import java.util.List;

import entities.Artwork;

/**
 * Comment Inpuut Boundary.
 */
public interface CommentInputBoundary {
    /**
     * Add comment.
     * @param commentInputData CommentInputData
     * @throws IOException failed comment
     */
    void addComment(CommentInputData commentInputData) throws IOException;

    /**
     * Get all commented artworks.
     * @return a list of artworks
     */
    List<Artwork> getCommentedArtworks();
}
