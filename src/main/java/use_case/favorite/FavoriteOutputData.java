package use_case.favorite;

import entities.Artwork;

public class FavoriteOutputData {
    private boolean favorited;

    public FavoriteOutputData(Artwork artwork) {
        favorited = artwork.checkFavorited();
    }

    public boolean getFavorited() {
        return favorited;
    }
}
