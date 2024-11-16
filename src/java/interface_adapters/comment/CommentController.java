package interface_adapters.comment;

import use_case.comment.CommentInputBoundary;
import use_case.comment.CommentInputData;

public class CommentController {
    private final CommentInputBoundary commentInteractor;

    public CommentController(CommentInputBoundary commentInteractor) {
        this.commentInteractor = commentInteractor;
    }

    public void execute(String artworkTitle, String comment) {
        final CommentInputData commentInputData = new CommentInputData(artworkTitle, comment);
        commentInteractor.addComment(commentInputData);
    }
}