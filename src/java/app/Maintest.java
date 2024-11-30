package app;

import data.FileArtworkDataAccessObject;
import data.MuseumDataAccessObject;
import entities.Artwork;

import java.io.IOException;

public class Maintest {
    public static void main(String[] args) throws IOException {
        MuseumDataAccessObject museum = new MuseumDataAccessObject();
        Artwork art = museum.getArtworkById("MET-560727");
        System.out.println(art.getTitle());

        FileArtworkDataAccessObject file = new FileArtworkDataAccessObject("src/favorite.json");
        file.save(art);

    }
}
