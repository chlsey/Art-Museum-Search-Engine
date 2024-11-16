package use_case.comment;
import entities.*;

public interface CommentDataAccessInterface {
    void addCommentToArtwork(String artworkTitle, String comment);
    Artwork getArtworkByTitle(String artworkTitle);
}
