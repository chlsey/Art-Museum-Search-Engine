package java.use_case.search;

import java.entities.*;
import java.util.ArrayList;

public class SearchInteractor implements SearchInputBoundary {
    private final SearchDataAccessInterface searchDataAccessObject;
    private final SearchOutputBoundary searchPresenter;

    public SearchInteractor(SearchDataAccessInterface searchDataAccessObject, SearchOutputBoundary searchPresenter) {
        this.searchDataAccessObject = searchDataAccessObject;
        this.searchPresenter = searchPresenter;
    }

    @Override
    public void execute(SearchInputData searchInputData) {
        ArrayList<Artwork> artworks = searchDataAccessObject.searchArtworks(searchInputData.getSearchMessage());

        final SearchOutputData searchOutputData = new SearchOutputData(artworks, false);
        searchPresenter.prepareSuccessView(searchOutputData);
    }
}
