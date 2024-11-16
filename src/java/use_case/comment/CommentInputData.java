package use_case.comment;

public class CommentInputData {
    private final String artworkTitle;
    private final String comment;

    public CommentInputData(String artworkTitle, String comment) {
        this.artworkTitle = artworkTitle;
        this.comment = comment;
    }

    public String getArtworkTitle() {
        return artworkTitle;
    }

    public String getComment() {
        return comment;
    }
}
