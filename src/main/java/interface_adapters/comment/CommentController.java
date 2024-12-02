package interface_adapters.comment;

import java.io.IOException;
import java.util.List;

import entities.Artwork;
import use_case.comment.CommentInputBoundary;
import use_case.comment.CommentInputData;

/**
 * Comment controller class.
 */
public class CommentController {
    private final CommentInputBoundary commentInteractor;

    public CommentController(CommentInputBoundary commentInteractor) {
        this.commentInteractor = commentInteractor;
    }

    /**
     * Execute.
     * @param artwork artwork
     * @param comment comment
     * @throws IOException exception
     */
    public void execute(Artwork artwork, String comment) throws IOException {
        final CommentInputData commentInputData = new CommentInputData(artwork, comment);
        commentInteractor.execute(commentInputData);
    }

    public List<Artwork> getCommentedArtworks() {
        return commentInteractor.getCommentedArtworks();
    }
}
