package use_case.comment;

public class CommentInputData {
    private final String username;
    private final String comment;

    public CommentInputData(String username, String comment) {
        this.username = username;
        this.comment = comment;
    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return comment;
    }
}
