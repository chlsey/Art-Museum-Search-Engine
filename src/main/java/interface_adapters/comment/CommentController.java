package interface_adapters.comment;

import entities.Artwork;
import use_case.comment.CommentInputBoundary;
import use_case.comment.CommentInputData;

import java.io.IOException;
import java.util.List;

public class CommentController {
    private final CommentInputBoundary commentInteractor;

    public CommentController(CommentInputBoundary commentInteractor) {
        this.commentInteractor = commentInteractor;
    }

    public void execute(Artwork artwork, String comment) throws IOException {
        final CommentInputData commentInputData = new CommentInputData(artwork, comment);
        commentInteractor.addComment(commentInputData);
    }

    public List<Artwork> getCommentedArtworks() {
        return commentInteractor.getCommentedArtworks();
    }
}