package interface_adapters;

import java.util.ArrayList;
import java.util.List;

/**
 * CFR state.
 */
public class CfrState {
    private List<String> comments = new ArrayList<>();
    private String artworkTitle;

    public CfrState() {

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

    /**
     * Add comment.
     * @param comment comment
     */
    public void addComment(String comment) {
        comments.add(comment);
    }

    /**
     * Set Rating.
     * @param selectedRating selected rating
     */
    public void setRating(int selectedRating) {

    }

    /**
     * Set favorited.
     * @param isFavorited is fovarited
     */
    public void setFavorited(boolean isFavorited) {

    }
}
