package use_case.search;

import java.util.List;

import entities.Artwork;

/**
 * The Output data.
 */
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

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
