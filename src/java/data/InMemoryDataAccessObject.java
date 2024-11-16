package data;

import entities.Artwork;
import use_case.favorite.FavoriteDataAccessInterface;
import use_case.search.SearchDataAccessInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InMemoryDataAccessObject implements SearchDataAccessInterface, FavoriteDataAccessInterface {

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
}
