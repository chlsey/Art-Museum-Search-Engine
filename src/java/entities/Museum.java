package entities;

import java.util.ArrayList;
import java.util.List;

public class Museum {
    private String name;
    private String location;
    private List<Artwork> artworks;

    public Museum(String name, String location) {
        this.name = name;
        this.location = location;
        this.artworks = new ArrayList<>();
    }

    public String getName() { return name; }
    public String getLocation() { return location; }
    public List<Artwork> getArtworks() { return artworks; }

    public void addArtwork(Artwork artwork) {
        artworks.add(artwork);
    }

    public void removeArtwork(Artwork artwork) {
        artworks.remove(artwork);
    }
}

