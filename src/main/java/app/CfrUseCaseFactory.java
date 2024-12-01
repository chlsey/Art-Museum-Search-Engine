package app;

import data.FileArtworkDataAccessObject;
import interface_adapters.CfrViewModel;
import interface_adapters.ViewManagerModel;
import interface_adapters.click_art.ClickArtViewModel;
import interface_adapters.comment.CommentController;
import interface_adapters.comment.CommentPresenter;
import interface_adapters.favorite.FavoriteController;
import interface_adapters.favorite.FavoritePresenter;
import interface_adapters.rating.RatingController;
import interface_adapters.rating.RatingPresenter;
import interface_adapters.search.SearchViewModel;
import use_case.comment.CommentInputBoundary;
import use_case.comment.CommentInteractor;
import use_case.comment.CommentOutputBoundary;
import use_case.favorite.FavoriteInputBoundary;
import use_case.favorite.FavoriteInteractor;
import use_case.favorite.FavoriteOutputBoundary;
import use_case.rating.RatingInputBoundary;
import use_case.rating.RatingInteractor;
import use_case.rating.RatingOutputBoundary;
import view.CfrView;

/**
 * Cfr use case factory.
 */
public class CfrUseCaseFactory {
    /**
     * Cfr view.
     * @param viewManagerModel the view manager model
     * @param searchViewModel searchViewModel
     * @param cfrViewModel cfrViewModel
     * @param clickArtViewModel clickArtViewModel
     * @param museumDataAccessObject museumDataAccessObject
     * @return crf view
     */
    public static CfrView create(ViewManagerModel viewManagerModel, SearchViewModel searchViewModel,
                                 CfrViewModel cfrViewModel, ClickArtViewModel clickArtViewModel,
                                 FileArtworkDataAccessObject museumDataAccessObject) {
        final CommentController commentController = createCommentUseCase(viewManagerModel,
                searchViewModel, clickArtViewModel, cfrViewModel, museumDataAccessObject);
        final RatingController ratingController = createRatingUseCase(viewManagerModel, searchViewModel,
                clickArtViewModel, cfrViewModel, museumDataAccessObject);
        final FavoriteController favoriteController = createFavoriteUseCase(viewManagerModel, searchViewModel,
                clickArtViewModel, cfrViewModel, museumDataAccessObject);
        return new CfrView(cfrViewModel, clickArtViewModel, ratingController, favoriteController,
                commentController);
    }

    private static FavoriteController createFavoriteUseCase(ViewManagerModel viewManagerModel,
                                                            SearchViewModel searchViewModel,
                                                            ClickArtViewModel clickViewModel,
                                                            CfrViewModel cfrViewModel,
                                                            FileArtworkDataAccessObject dataAccessObject) {
        final FavoriteOutputBoundary favoriteOutputBoundary = new FavoritePresenter(cfrViewModel, viewManagerModel,
                clickViewModel, searchViewModel);
        final FavoriteInputBoundary favoriteInteractor = new FavoriteInteractor(dataAccessObject,
                favoriteOutputBoundary);

        return new FavoriteController(favoriteInteractor);
    }

    private static CommentController createCommentUseCase(ViewManagerModel viewManagerModel,
                                                          SearchViewModel searchViewModel,
                                                          ClickArtViewModel clickViewModel,
                                                          CfrViewModel cfrViewModel,
                                                          FileArtworkDataAccessObject dataAccessObject) {
        // Create the output boundary (Presenter) to handle UI updates and view transitions
        final CommentOutputBoundary commentOutputBoundary = new CommentPresenter(cfrViewModel, viewManagerModel,
                clickViewModel, searchViewModel);
        // Create the input boundary (Interactor) to handle business logic for fetching and preparing data
        final CommentInputBoundary commentInteractor = new CommentInteractor(dataAccessObject, commentOutputBoundary);

        // Create and return the controller which handles user interactions
        return new CommentController(commentInteractor);
    }

    private static RatingController createRatingUseCase(ViewManagerModel viewManagerModel,
                                                        SearchViewModel searchViewModel,
                                                        ClickArtViewModel clickViewModel,
                                                        CfrViewModel cfrViewModel,
                                                        FileArtworkDataAccessObject dataAccessObject) {
        final RatingOutputBoundary ratingOutputBoundary = new RatingPresenter(cfrViewModel, viewManagerModel,
                clickViewModel, searchViewModel);
        final RatingInputBoundary ratingInteractor = new RatingInteractor(dataAccessObject, ratingOutputBoundary);

        return new RatingController(ratingInteractor);
    }
}
