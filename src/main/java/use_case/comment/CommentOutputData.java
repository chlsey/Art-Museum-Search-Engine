package use_case.comment;

/**
 * Comment Output Data class.
 */
public class CommentOutputData {
    private final String artworkTitle;
    private final String comment;
    private final boolean success;

    public CommentOutputData(String artworkTitle, String comment, boolean success) {
        this.artworkTitle = artworkTitle;
        this.comment = comment;
        this.success = success;
    }

    public String getArtworkTitle() {
        return artworkTitle;
    }

    public String getComment() {
        return comment;
    }

    public boolean isSuccess() {
        return success;
    }
}
