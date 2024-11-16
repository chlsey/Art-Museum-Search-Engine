package use_case.search;

import entities.Artwork;

import java.util.ArrayList;
import java.util.List;

public class SearchOutputData {

    private final List<Artwork> artworks;
    private final boolean useCaseFailed;

    public SearchOutputData(List<Artwork> artworks, boolean useCaseFailed) {
        this.artworks = artworks;
        this.useCaseFailed = useCaseFailed;
    }

    public List<Artwork> getArtworks() {
        return artworks;
    }

}
