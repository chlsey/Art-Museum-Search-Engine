package interface_adapters.comment;

import java.util.ArrayList;
import java.util.List;

/**
 * Comment state class.
 */
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

    /**
     * Get artwork title.
     * @return artwork title
     */
    public String getArtworkTitle() {
        return artworkTitle;
    }

    /**
     * Add comment.
     * @param comment comment
     */
    public void addComment(String comment) {
        comments.add(comment);
    }

    /**
     * Set rating.
     * @param selectedRating selectedRating
     */
    public void setRating(int selectedRating) {

    }

    /**
     * Set favorited.
     * @param isFavorited isFavorited
     */
    public void setFavorited(boolean isFavorited) {

    }
}
