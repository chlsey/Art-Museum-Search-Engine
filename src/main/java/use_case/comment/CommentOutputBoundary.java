package use_case.comment;

/**
 * Comment output.
 */
public interface CommentOutputBoundary {
    /**
     * Present success view.
     * @param outputData output data
     */
    void presentSuccessView(CommentOutputData outputData);

    /**
     * Present failure view.
     * @param errorMessage errorMessage
     */
    void presentFailureView(String errorMessage);
}
