package use_case.comment;

public interface CommentOutputBoundary {
    void presentSuccessView(CommentOutputData outputData);
    void presentFailureView(String errorMessage);
}
