package data;

import entities.Artwork;
import use_case.search.SearchDataAccessInterface;

import java.util.ArrayList;

public class InMemoryDataAccessObject implements SearchDataAccessInterface {

    private ArrayList<Artwork> artworks;

    public InMemoryDataAccessObject() {
        this.artworks = new ArrayList<>();
    }

    @Override
    public void favorite(Artwork artwork) {
        this.artworks.add(artwork);
    }
}
