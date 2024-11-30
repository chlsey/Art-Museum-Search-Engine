package use_case.favorite;

import entities.Artwork;

public class FavoriteInputData {
    private final Artwork artwork;

    public FavoriteInputData(Artwork artwork) {
        this.artwork = artwork;
    }

    public Artwork getArtwork() {
        return artwork;
    }
}
