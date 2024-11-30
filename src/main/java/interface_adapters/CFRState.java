package interface_adapters;

import java.util.ArrayList;
import java.util.List;

public class CFRState {
    private List<String> comments = new ArrayList<>();
    private String artworkTitle;

    public CFRState() {

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

    public void setRating(int selectedRating) {

    }

    public void setFavorited(boolean isFavorited) {

    }
}
