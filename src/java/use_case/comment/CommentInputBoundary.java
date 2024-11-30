package use_case.comment;

import entities.Artwork;

import java.io.IOException;
import java.util.List;

public interface CommentInputBoundary {
    void addComment(CommentInputData commentInputData) throws IOException;

    List<Artwork> getCommentedArtworks();
}
