package use_case.comment;

public interface CommentInputBoundary {
    void execute(CommentInputBoundary commentInputBoundary);

    void execute(CommentInputData commentInputData);
}
