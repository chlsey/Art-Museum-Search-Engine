package use_case.favorite;

import java.io.IOException;

public interface FavoriteInputBoundary {

    void execute(FavoriteInputData favoriteInputData) throws IOException;

}
