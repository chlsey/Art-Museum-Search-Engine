package data;

import entities.Artwork;
import use_case.click_art.ClickArtDataAccessInterface;
import use_case.favorite.FavoriteDataAccessInterface;
import use_case.comment.CommentDataAccessInterface;
import use_case.search.SearchDataAccessInterface;
import use_case.rating.RatingDataAccessInterface;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryDataAccessObject implements SearchDataAccessInterface, FavoriteDataAccessInterface, CommentDataAccessInterface, RatingDataAccessInterface, ClickArtDataAccessInterface {

    private Map<String, Artwork> artworks = new HashMap<>();

    @Override
    public void updateFavorite(Artwork artwork) {
        artwork.setFavorited();
    }

    @Override
    public void save(Artwork artwork) {
        artworks.put(artwork.getTitle(), artwork);
    }

    public boolean contains(Artwork artwork) {
        return artworks.containsKey(artwork.getTitle());
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
        for (Artwork artwork : artworks.values()) {
            if (artwork.getTitle().equalsIgnoreCase(artworkTitle)) {
                return artwork;
            }
        }
        return null;
    }

    @Override
    public int getRating() {
        return 0;
    }

    @Override
    public void incrementRatingCount(int ratingValue) {

    }

    @Override
    public double calculateAverageRating() {
        return 0;
    }

    @Override
    public List<Artwork> searchArtwork(String searchMessage, String hasimages) {
        return List.of();
    }
}
