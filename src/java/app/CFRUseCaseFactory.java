package app;

import data.MuseumDataAccessObject;
import interface_adapters.CFRViewModel;
import interface_adapters.ViewManagerModel;
import interface_adapters.click_art.ClickArtController;
import interface_adapters.click_art.ClickArtPresenter;
import interface_adapters.click_art.ClickArtViewModel;
import interface_adapters.comment.CommentController;
import interface_adapters.comment.CommentPresenter;
import interface_adapters.search.SearchViewModel;
import use_case.click_art.ClickArtInputBoundary;
import use_case.click_art.ClickArtInteractor;
import use_case.click_art.ClickArtOutputBoundary;
import use_case.comment.CommentInputBoundary;
import use_case.comment.CommentInteractor;
import use_case.comment.CommentOutputBoundary;
import view.CFRView;
import view.ClickView;

public class CFRUseCaseFactory {
    public static CFRView create(ViewManagerModel viewManagerModel, SearchViewModel searchViewModel,
                                 CFRViewModel cfrViewModel, ClickArtViewModel clickArtViewModel,
                                 MuseumDataAccessObject museumDataAccessObject) {
        final CommentController commentController = createCommentUseCase(viewManagerModel, searchViewModel, clickArtViewModel, cfrViewModel, museumDataAccessObject);
        return new CFRView(cfrViewModel);
    }

    private static CommentController createCommentUseCase(ViewManagerModel viewManagerModel,
                                                         SearchViewModel searchViewModel,
                                                         ClickArtViewModel clickViewModel,
                                                         CFRViewModel cfrViewModel,
                                                         MuseumDataAccessObject dataAccessObject) {
        // Create the output boundary (Presenter) to handle UI updates and view transitions
        final CommentOutputBoundary commentOutputBoundary = new CommentPresenter(cfrViewModel,viewManagerModel);
        // Create the input boundary (Interactor) to handle business logic for fetching and preparing data
        final CommentInputBoundary commentInteractor = new CommentInteractor(dataAccessObject, commentOutputBoundary);

        // Create and return the controller which handles user interactions
        return new CommentController(commentInteractor);
    }
}
