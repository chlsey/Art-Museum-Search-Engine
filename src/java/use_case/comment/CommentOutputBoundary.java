package use_case.comment;

public interface CommentOutputBoundary {
    void presentSuccess(CommentOutputData outputData);
    void presentFailure(String errorMessage);
}
