package use_case.comment;

public class CommentInteractor {
    private final CommentDataAccessInterface commentDataAccessInterface;
    private final CommentOutputBoundary commentPresenter;

    public CommentInteractor(CommentDataAccessInterface commentDataAccessInterface,
                             CommentOutputBoundary commentPresenter) {
        this.commentDataAccessInterface = commentDataAccessInterface;
        this.commentPresenter = commentPresenter;
    }

}
