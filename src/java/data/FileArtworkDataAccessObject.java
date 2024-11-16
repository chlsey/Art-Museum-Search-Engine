package data;

import entities.Artwork;
import entities.ArtworkFactory;
import use_case.favorite.FavoriteDataAccessInterface;
import use_case.search.SearchDataAccessInterface;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class FileArtworkDataAccessObject implements SearchDataAccessInterface, FavoriteDataAccessInterface {
    private static final String HEADER = "title,artistname,timeperiod,gallery,imageURL,comment,keywords";

    private final File txtFile;
    private final Map<String, Integer> headers = new LinkedHashMap<>();
    private final Map<String, Artwork> artworks = new HashMap<>();

    public FileArtworkDataAccessObject(String txtPath) throws IOException {
        txtFile = new File(txtPath);
        headers.put("title", 0);
        headers.put("artistname", 1);
        headers.put("timeperiod", 2);
        headers.put("gallery", 3);
        headers.put("imageURL", 4);
        headers.put("comment", 5);
        headers.put("keywords", 6);

        if (txtFile.length() == 0) {
            save();
        }
        else {

            try (BufferedReader reader = new BufferedReader(new FileReader(txtFile))) {
                final String header = reader.readLine();

                if (!header.equals(HEADER)) {
                    throw new RuntimeException(String.format("header should be%n: %s%but was:%n%s", HEADER, header));
                }

                String row;
                while ((row = reader.readLine()) != null) {
                    final String[] col = row.split(",");
                    final String title = String.valueOf(col[headers.get("title")]);
                    final String artistname = String.valueOf(col[headers.get("artistname")]);
                    final String timeperiod = String.valueOf(col[headers.get("timeperiod")]);
                    final String gallery = String.valueOf(col[headers.get("gallery")]);
                    final String image = String.valueOf(col[headers.get("imageURL")]);
                    final String comment = String.valueOf(col[headers.get("comment")]);
                    final String keywords = String.valueOf(col[headers.get("keywords")]);
                    // TODO: must implement multiple keywords function
                    final Artwork artwork = new Artwork(title, artistname, timeperiod,
                            gallery, image, keywords, comment); // TODO: could implement artwork factory instead.
                    artworks.put(title, artwork); // TODO: could put something else in the place of title to make it easier to search things up.
                }
            }
        }
    }

    private void save() {
        final BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(txtFile));
            writer.write(String.join(",", headers.keySet()));
            writer.newLine();

            for (Artwork artwork : artworks.values()) {
                String line = String.format("%s,%s,%s,%s,%s,%s",
                        artwork.getTitle(),
                        artwork.getArtistName(),
                        artwork.getCompositionDate(),
                        artwork.getGallery(),
                        artwork.getImageUrl(),
                        artwork.getComment());
                // TODO: implement list of keywords for each artwork (String key : artwork.getKeyWords())
                writer.write(line);
                writer.newLine();
            }

            writer.close();

        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void comment(Artwork artwork, String comment) {
    }

    @Override
    public void updateFavorite(Artwork artwork) {
        if (!artworks.containsKey(artwork.getTitle())) {
            artworks.put(artwork.getTitle(), artwork);
            this.save();
        }
    }

    @Override
    public void save(Artwork artwork) {

    }
}
