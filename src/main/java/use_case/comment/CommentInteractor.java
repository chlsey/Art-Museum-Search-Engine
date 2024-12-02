package use_case.comment;

import entities.Artwork;

import java.io.IOException;
import java.util.List;

public class CommentInteractor implements CommentInputBoundary {
    private final CommentDataAccessInterface commentDataAccessObject;
    private final CommentOutputBoundary commentPresenter;

    public CommentInteractor(CommentDataAccessInterface commentDataAccessObject,
                             CommentOutputBoundary commentOutputBoundary) {
        this.commentDataAccessObject = commentDataAccessObject;
        this.commentPresenter = commentOutputBoundary;
    }

    @Override
    public void addComment(CommentInputData commentInputData) throws IOException {
        final String artworkTitle = commentInputData.getArtworkTitle();
        final String comment = commentInputData.getComment();
        Artwork artwork = commentDataAccessObject.getArtworkById(commentInputData.getArtwork().getId());

        if (comment.isEmpty()) {
            commentPresenter.presentFailureView("Comment cannot be empty");
            return;
        }

        commentDataAccessObject.addCommentToArtwork(artwork, comment);

        CommentOutputData outputData = new CommentOutputData(artworkTitle, comment, true);
        commentPresenter.presentSuccessView(outputData);
    }

    @Override
    public List<Artwork> getCommentedArtworks() {
        return commentDataAccessObject.getCommentedArtworks();
    }
}