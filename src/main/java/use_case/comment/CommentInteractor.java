package use_case.comment;

import java.io.IOException;
import java.util.List;

import entities.Artwork;

/**
 * Comment Interactor.
 */
public class CommentInteractor implements CommentInputBoundary {
    private final CommentDataAccessInterface commentDataAccessObject;
    private final CommentOutputBoundary commentPresenter;

    public CommentInteractor(CommentDataAccessInterface commentDataAccessObject,
                             CommentOutputBoundary commentOutputBoundary) {
        this.commentDataAccessObject = commentDataAccessObject;
        this.commentPresenter = commentOutputBoundary;
    }

    /**
     * Add comment.
     * @param commentInputData commentInputData
     * @throws IOException exception
     */
    @Override
    public void addComment(CommentInputData commentInputData) throws IOException {
        final String artworkTitle = commentInputData.getArtworkTitle();
        final String comment = commentInputData.getComment();
        final Artwork artwork = commentDataAccessObject.getArtworkById(commentInputData.getArtwork().getId());

        if (comment.isEmpty()) {
            commentPresenter.presentFailureView("Comment cannot be empty");
            return;
        }

        commentDataAccessObject.addCommentToArtwork(artwork, comment);
        final CommentOutputData outputData = new CommentOutputData(artworkTitle, comment, true);
        commentPresenter.presentSuccessView(outputData);
    }

    @Override
    public List<Artwork> getCommentedArtworks() {
        return commentDataAccessObject.getCommentedArtworks();
    }
}
