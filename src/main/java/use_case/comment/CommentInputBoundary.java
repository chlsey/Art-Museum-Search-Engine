package use_case.comment;

import java.io.IOException;

public interface CommentInputBoundary {
    void addComment(CommentInputData commentInputData) throws IOException;
}
