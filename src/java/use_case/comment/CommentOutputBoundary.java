package use_case.comment;

public interface CommentOutputBoundary {
    void prepareSuccessView(CommentOutputData data);
    void prepareErrorView(CommentOutputData data);
}
