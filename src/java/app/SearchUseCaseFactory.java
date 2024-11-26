package app;

import data.MuseumDataAccessObject;
import interface_adapters.ViewManagerModel;
import interface_adapters.click_art.ClickArtViewModel;
import interface_adapters.filter.FilterController;
import interface_adapters.filter.FilterPresenter;
import interface_adapters.search.SearchController;
import interface_adapters.search.SearchPresenter;
import interface_adapters.search.SearchViewModel;
import use_case.filter.FilterInputBoundary;
import use_case.filter.FilterInteractor;
import use_case.filter.FilterOutputBoundary;
import use_case.search.*;
import view.SearchView;

public class SearchUseCaseFactory {

    public static SearchView create(ViewManagerModel viewManagerModel, SearchViewModel searchViewModel,
                                    MuseumDataAccessObject museumDataAccessObject, ClickArtViewModel clickArtViewModel) {
        final SearchController searchController = createSearchUseCase(viewManagerModel, searchViewModel, museumDataAccessObject, clickArtViewModel);
        final FilterController filterController = createFilterUseCase(viewManagerModel, searchViewModel, museumDataAccessObject, clickArtViewModel);
        return new SearchView(searchController, searchViewModel, clickArtViewModel, filterController);
    }

    private static FilterController createFilterUseCase(ViewManagerModel viewManagerModel,
                                                        SearchViewModel searchViewModel,
                                                        MuseumDataAccessObject museumDataAccessObject,
                                                        ClickArtViewModel clickArtViewModel) {
        final FilterOutputBoundary filterOutputBoundary = new FilterPresenter(searchViewModel,viewManagerModel, clickArtViewModel);
        final FilterInputBoundary filterInteractor = new FilterInteractor(museumDataAccessObject, filterOutputBoundary);
        return new FilterController(filterInteractor);
    }

    private static SearchController createSearchUseCase(ViewManagerModel viewManagerModel,
                                                        SearchViewModel searchViewModel,
                                                        MuseumDataAccessObject museumDataAccessObject,
                                                        ClickArtViewModel clickArtViewModel) {
        final SearchOutputBoundary searchOutputBoundary = new SearchPresenter(viewManagerModel, searchViewModel, clickArtViewModel);
        final SearchInputBoundary searchInteractor = new SearchInteractor(museumDataAccessObject, searchOutputBoundary);

        return new SearchController(searchInteractor);
    }
}
