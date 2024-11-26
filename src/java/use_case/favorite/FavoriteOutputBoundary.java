package use_case.favorite;

import java.io.IOException;

public interface FavoriteOutputBoundary {
    void prepareSuccessView(FavoriteOutputData outputData) throws IOException;

    void prepareFailView(String errorMessage);


}
