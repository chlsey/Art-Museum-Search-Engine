package data;

import entities.Artwork;
import use_case.comment.CommentDataAccessInterface;
import use_case.search.SearchDataAccessInterface;

import java.util.ArrayList;

public class InMemoryDataAccessObject implements SearchDataAccessInterface,CommentDataAccessInterface {

    private ArrayList<Artwork> artworks;

    public InMemoryDataAccessObject() {
        this.artworks = new ArrayList<>();
    }

    @Override
    public void favorite(Artwork artwork) {
        this.artworks.add(artwork);
    }

    @Override
    public void addCommentToArtwork(String artworkTitle, String comment) {
        Artwork artwork = getArtworkByTitle(artworkTitle);
        if (artwork == null) {
            throw new IllegalArgumentException("Artwork with title '" + artworkTitle + "' does not exist.");
        }
        artwork.addComment(comment);
    }

    @Override
    public Artwork getArtworkByTitle(String artworkTitle) {
        for (Artwork artwork : artworks) {
            if (artwork.getTitle().equalsIgnoreCase(artworkTitle)) {
                return artwork;
            }
        }
        return null;
    }
}
