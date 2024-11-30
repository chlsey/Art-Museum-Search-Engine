package data;

import entities.Artwork;
import use_case.click_art.ClickArtDataAccessInterface;
import use_case.favorite.FavoriteDataAccessInterface;
import use_case.comment.CommentDataAccessInterface;
import use_case.search.SearchDataAccessInterface;
import use_case.rating.RatingDataAccessInterface;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryDataAccessObject implements SearchDataAccessInterface, FavoriteDataAccessInterface, CommentDataAccessInterface, RatingDataAccessInterface, ClickArtDataAccessInterface {

    private Map<String, Artwork> artworks = new HashMap<>();

    @Override
    public void updateFavorite(Artwork artwork) {
        if (contains(artwork.getId())) {
            artworks.get(artwork.getId()).setFavorited(!artwork.checkFavorited());
        }
        else {
            artwork.setFavorited(!artwork.checkFavorited());
            save(artwork);
        }
    }

    @Override
    public void save(Artwork artwork) {
        artworks.put(artwork.getTitle(), artwork);
    }

    public boolean contains(String id) {
        return artworks.containsKey(id);
    }

    @Override
    public List<Artwork> getAllFavorites() {
        return List.of();
    }

    @Override
    public void addCommentToArtwork(Artwork artwork, String comment) {
        Artwork art = getArtworkById(artwork.getId());
        if (artwork == null) {
            throw new IllegalArgumentException("Artwork with id '" + artwork.getId() + "' does not exist.");
        }
        artwork.addComment(comment);
    }

    @Override
    public Artwork getArtworkById(String id) {
        try {
            return artworks.get(id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Artwork> getCommentedArtworks() {
        return List.of();
    }

    @Override
    public int getRating(Artwork artwork) throws IOException {
        return 0;
    }

    @Override
    public void setRating(int rating) {

    }

    @Override
    public List<Artwork> getRatedArtworks() {
        return List.of();
    }

//    @Override
//    public void incrementRatingCount(int ratingValue) {
//
//    }
//
//    @Override
//    public double calculateAverageRating() {
//        return 0;
//    }

    @Override
    public List<Artwork> searchArtwork(String searchMessage) {
        return List.of();
    }
}
