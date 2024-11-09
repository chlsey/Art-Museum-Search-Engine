package entities;
import java.util.ArrayList;
import java.util.List;


public class Artist {
    private String name;
    private String lifeSpan;
    private List<Artwork> artworks;
    private String gender;

    public Artist(String name, String lifeSpan, String[] artworks, String gender) {
        this.name = name;
        this.lifeSpan = lifeSpan;
        this.artworks = new ArrayList<>();
        this.gender = gender;
    }
    public String getName() {
        return name;
    }

    public String getLifeSpan() {
        return lifeSpan;
    }
    public List<Artwork> getArtworks() {
        return artworks;
    }
    public String getGender() {
        return gender;
    }
}

