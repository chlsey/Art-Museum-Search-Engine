package use_case.comment;

import entities.Artwork;

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
    public void addComment(CommentInputData commentInputData) {
        final String artworkTitle = commentInputData.getArtworkTitle();
        final String comment = commentInputData.getComment();
        Artwork artwork = commentDataAccessObject.getArtworkByTitle(artworkTitle);

        commentDataAccessObject.addCommentToArtwork(artworkTitle, comment);

        CommentOutputData outputData = new CommentOutputData(artworkTitle, comment, true);
        commentPresenter.presentSuccess(outputData);
    }


}
