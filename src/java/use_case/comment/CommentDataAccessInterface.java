package use_case.comment;
import entities.*;

import java.io.IOException;
import java.util.List;

public interface CommentDataAccessInterface {
    void addCommentToArtwork(Artwork artwork, String comment) throws IOException;
    Artwork getArtworkById(String id) throws IOException;

    List<Artwork> getCommentedArtworks();
}
