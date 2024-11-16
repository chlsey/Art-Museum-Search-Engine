package interface_adapters.comment;

import java.util.ArrayList;
import java.util.List;

public class CommentState {
    private List<String> comments = new ArrayList<>();
    private String artworkTitle;

    public CommentState(String artworkTitle) {
        this.artworkTitle = artworkTitle;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public List<String> getComments() {
        return comments;
    }

    public String getArtworkTitle() {
        return artworkTitle;
    }

    public void addComment(String comment) {
        comments.add(comment);
    }

    //public void setCommentError(String errorMessage) {
    //    System.out.println(errorMessage);
    //}
}
