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
            artworks.get(artwork.getId()).setFavorited();
        }
        else {
            artwork.setFavorited();
            save(artwork);
        }
    }

    @Override
    public void save(Artwork artwork) {
        artworks.put(artwork.getId(), artwork);
    }

    public boolean contains(String id) {
        return artworks.containsKey(id);
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


//    @Override
//    public void updateRating(Artwork artwork) {
//
//    }

    @Override
    public void saveRating(Artwork artwork) throws IOException {

    }

    @Override
    public int getRating() {
        return 0;
    }

    @Override
    public void setRating(int rating) {

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
