package java.use_case.search;

import java.entities.Artwork;
import java.util.ArrayList;

public class SearchOutputData {

    private final ArrayList<Artwork> artworks;
    private final boolean useCaseFailed;

    public SearchOutputData(ArrayList<Artwork> artworks, boolean useCaseFailed) {
        this.artworks = artworks;
        this.useCaseFailed = useCaseFailed;
    }

    public ArrayList<Artwork> getArtworks() {
        return artworks;
    }

}
