package use_case.comment;
import entities.Artwork;

public class CommentInputData {
    private final String comment;
    private final String author;

    public CommentInputData(String comment, String author) {
        this.comment = comment;
        this.author = author;
    }

    public String getComment() {
        return comment;
    }
    public String getAuthor() {
        return author;
    }

}
