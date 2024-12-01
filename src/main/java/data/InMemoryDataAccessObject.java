package data;

import entities.Artwork;
import use_case.click_art.ClickArtDataAccessInterface;
import use_case.favorite.FavoriteDataAccessInterface;
import use_case.comment.CommentDataAccessInterface;
import use_case.filter.FilterDataAccessInterface;
import use_case.search.SearchDataAccessInterface;
import use_case.rating.RatingDataAccessInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryDataAccessObject implements SearchDataAccessInterface, FavoriteDataAccessInterface, RatingDataAccessInterface, ClickArtDataAccessInterface, FilterDataAccessInterface,CommentDataAccessInterface {

    private Map<String, Artwork> artworks = new HashMap<>();

    @Override
    public void updateFavorite(String id) {
        Artwork artwork = artworks.get(id);
        if (artwork != null) {
            artwork.setFavorited(!artwork.checkFavorited());
        } else {
            System.out.println("Artwork with ID " + id + " does not exist.");
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
    public List<Artwork> getAllFavorites() {
        List<Artwork> favorites = new ArrayList<>();
        for (Artwork artwork : artworks.values()) {
            if (artwork.checkFavorited()) {
                favorites.add(artwork);
            }
        }
        return favorites;
    }

    @Override
    public void addCommentToArtwork(Artwork artwork, String comment) {
        Artwork art = getArtworkById(artwork.getId());
        if (art == null) {
            throw new IllegalArgumentException("Artwork with id '" + artwork.getId() + "' does not exist.");
        }
        art.addComment(comment);
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
    public List<Artwork> getRatedArtworks() {

        List<Artwork> ratedArtworks = new ArrayList<>();
        for (Artwork artwork : artworks.values()) {
            if (artwork.getRating() > 0) {
                ratedArtworks.add(artwork);
            }
        }
        return ratedArtworks;
    }

    @Override
    public void updateRating(String id, int rating) throws IOException {
        if (contains(id)) {
            artworks.get(id).setRating(rating);
        } else {
            MuseumDataAccessObject DAO = new MuseumDataAccessObject();
            Artwork artwork = DAO.getArtworkById(id);
            artwork.setRating(rating);
            save(artwork);
        }
    }


    @Override
    public List<Artwork> searchArtwork(String searchMessage) {
        List<Artwork> result = new ArrayList<>();
        Map<Artwork,List<String>> map = new HashMap<>();
        for (Artwork artwork : artworks.values()) {
            List<String> keywords = new ArrayList<>();
            keywords.add(artwork.getTitle());
            keywords.add(artwork.getDescription());
            keywords.add(artwork.getKeyWords());
            keywords.add(artwork.getArtistName());
            map.put(artwork, keywords);
        }
        for (Artwork artwork : map.keySet()) {
            List<String> keywords = map.get(artwork);
            if (keywords.contains(searchMessage)) {
                result.add(artwork);
            }
        }
        return result;
    }

    @Override
    public void changeFilter(String spec) {

    }







}