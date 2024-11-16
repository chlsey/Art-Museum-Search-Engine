package use_case.favorite;

public interface FavoriteOutputBoundary {
    void prepareSuccessView(FavoriteOutputData outputData);

    void prepareFailView(String errorMessage);


}
