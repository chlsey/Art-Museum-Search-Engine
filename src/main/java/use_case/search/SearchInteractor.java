
package use_case.search;

import java.util.List;

import entities.Artwork;

/**
 * The interactor of search which implement input boundary.
 */
public class SearchInteractor implements SearchInputBoundary {
    private final SearchDataAccessInterface searchDataAccessObject;
    private final SearchOutputBoundary searchPresenter;

    public SearchInteractor(SearchDataAccessInterface searchDataAccessObject, SearchOutputBoundary searchPresenter) {
        this.searchDataAccessObject = searchDataAccessObject;
        this.searchPresenter = searchPresenter;
    }

    @Override
    public void execute(SearchInputData searchInputData) {
        final List<Artwork> ourartworks = searchDataAccessObject.searchArtwork(searchInputData.getSearchMessage());
        final boolean failed = false;
        final SearchOutputData searchOutputData = new SearchOutputData(ourartworks, failed);
        searchPresenter.prepareSuccessView(searchOutputData);
    }
}
