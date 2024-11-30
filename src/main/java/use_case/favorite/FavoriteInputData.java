package use_case.favorite;

import entities.Artwork;

public class FavoriteInputData {
    private final String id;

    public FavoriteInputData(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
