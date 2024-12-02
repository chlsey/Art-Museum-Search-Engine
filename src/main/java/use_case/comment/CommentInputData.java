package use_case.comment;

import entities.Artwork;

/**
 * Comment input data.
 */
public class CommentInputData {
    private final String artworkTitle;
    private final String comment;
    private final Artwork artwork;

    public CommentInputData(Artwork artwork, String comment) {
        this.artworkTitle = artwork.getTitle();
        this.comment = comment;
        this.artwork = artwork;
    }

    public String getArtworkTitle() {
        return artworkTitle;
    }

    public String getComment() {
        return comment;
    }

    public Artwork getArtwork() {
        return artwork;
    }
}
