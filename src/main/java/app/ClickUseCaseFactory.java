package app;

import data.MuseumDataAccessObject;
import interface_adapters.CFRViewModel;
import interface_adapters.ViewManagerModel;
import interface_adapters.click_art.ClickArtController;
import interface_adapters.click_art.ClickArtPresenter;
import interface_adapters.click_art.ClickArtViewModel;
import interface_adapters.search.SearchViewModel;
import use_case.click_art.*;

import use_case.click_art.ClickArtInputBoundary;
import use_case.click_art.ClickArtInteractor;
import use_case.click_art.ClickArtOutputBoundary;
import view.ClickView;

/**
 * Factory class to set up the Click Art use case.
 */
public class ClickUseCaseFactory {

    public static ClickView create(ViewManagerModel viewManagerModel, SearchViewModel searchViewModel, CFRViewModel cfrViewModel, ClickArtViewModel clickViewModel,
                                   MuseumDataAccessObject dataAccessObject) {
        // Create the controller for handling click actions
        final ClickArtController clickController = createClickUseCase(viewManagerModel, searchViewModel, clickViewModel, cfrViewModel, dataAccessObject);
        // Create and return the view connected to the controller and ViewModel
        return new ClickView(clickController, clickViewModel);
    }

    private static ClickArtController createClickUseCase(ViewManagerModel viewManagerModel,
                                                         SearchViewModel searchViewModel,
                                                         ClickArtViewModel clickViewModel,
                                                         CFRViewModel cfrViewModel,
                                                         MuseumDataAccessObject dataAccessObject) {
        // Create the output boundary (Presenter) to handle UI updates and view transitions
        final ClickArtOutputBoundary clickArtOutputBoundary = new ClickArtPresenter(searchViewModel, clickViewModel, cfrViewModel,viewManagerModel);
        // Create the input boundary (Interactor) to handle business logic for fetching and preparing data
        final ClickArtInputBoundary clickArtInteractor = new ClickArtInteractor(dataAccessObject, clickArtOutputBoundary);

        // Create and return the controller which handles user interactions
        return new ClickArtController(clickArtInteractor);
    }
}
