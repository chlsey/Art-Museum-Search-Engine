package use_case.favorite;

import entities.Artwork;

import java.io.IOException;
import java.util.List;

public interface FavoriteInputBoundary {

    void execute(FavoriteInputData favoriteInputData) throws IOException;
    void getFavoriteArtworks();

}
