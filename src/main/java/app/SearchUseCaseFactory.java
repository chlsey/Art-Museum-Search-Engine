package app;

import data.FileArtworkDataAccessObject;
import data.MuseumDataAccessObject;
import interface_adapters.CfrViewModel;
import interface_adapters.ViewManagerModel;
import interface_adapters.click_art.ClickArtViewModel;
import interface_adapters.comment.CommentController;
import interface_adapters.comment.CommentPresenter;
import interface_adapters.favorite.FavoriteController;
import interface_adapters.favorite.FavoritePresenter;
import interface_adapters.filter.FilterController;
import interface_adapters.filter.FilterPresenter;
import interface_adapters.rating.RatingController;
import interface_adapters.rating.RatingPresenter;
import interface_adapters.search.SearchController;
import interface_adapters.search.SearchPresenter;
import interface_adapters.search.SearchViewModel;
import use_case.comment.CommentInputBoundary;
import use_case.comment.CommentInteractor;
import use_case.comment.CommentOutputBoundary;
import use_case.favorite.FavoriteInputBoundary;
import use_case.favorite.FavoriteInteractor;
import use_case.favorite.FavoriteOutputBoundary;
import use_case.filter.FilterInputBoundary;
import use_case.filter.FilterInteractor;
import use_case.filter.FilterOutputBoundary;
import use_case.rating.RatingInputBoundary;
import use_case.rating.RatingInteractor;
import use_case.rating.RatingOutputBoundary;
import use_case.search.SearchInputBoundary;
import use_case.search.SearchInteractor;
import use_case.search.SearchOutputBoundary;
import view.SearchView;

/**
 * Search use case factory class.
 */
public class SearchUseCaseFactory {
    /**
     * Search view state.
     * @param viewManagerModel viewManagerModel
     * @param searchViewModel searchViewModel
     * @param museumDataAccessObject museumDataAccessObject
     * @param clickArtViewModel clickArtViewModel
     * @param fileArtworkDataAccessObject fileArtworkDataAccessObject
     * @param cfrViewModel cfrViewModel
     * @return view
     */
    public static SearchView create(ViewManagerModel viewManagerModel, SearchViewModel searchViewModel,
                                    MuseumDataAccessObject museumDataAccessObject, ClickArtViewModel clickArtViewModel,
                                    FileArtworkDataAccessObject fileArtworkDataAccessObject,
                                    CfrViewModel cfrViewModel) {
        final SearchController searchController = createSearchUseCase(viewManagerModel, searchViewModel,
                museumDataAccessObject, clickArtViewModel);
        final FilterController filterController = createFilterUseCase(viewManagerModel, searchViewModel,
                museumDataAccessObject, clickArtViewModel);
        final FavoriteController favoriteController = createFavoriteUseCase(viewManagerModel, searchViewModel,
                cfrViewModel, clickArtViewModel, fileArtworkDataAccessObject);
        final RatingController ratingController = createRatingUseCase(viewManagerModel, searchViewModel,
                cfrViewModel, clickArtViewModel, fileArtworkDataAccessObject);
        final CommentController commentController = createCommentUseCase(viewManagerModel, searchViewModel,
                cfrViewModel, clickArtViewModel, fileArtworkDataAccessObject);
        return new SearchView(searchController, searchViewModel, clickArtViewModel, filterController,
                favoriteController, ratingController, commentController);
    }

    private static FilterController createFilterUseCase(ViewManagerModel viewManagerModel,
                                                        SearchViewModel searchViewModel,
                                                        MuseumDataAccessObject museumDataAccessObject,
                                                        ClickArtViewModel clickArtViewModel) {
        final FilterOutputBoundary filterOutputBoundary = new FilterPresenter(searchViewModel, viewManagerModel,
                clickArtViewModel);
        final FilterInputBoundary filterInteractor = new FilterInteractor(museumDataAccessObject, filterOutputBoundary);
        return new FilterController(filterInteractor);
    }

    private static FavoriteController createFavoriteUseCase(ViewManagerModel viewManagerModel,
                                                            SearchViewModel searchViewModel,
                                                            CfrViewModel cfrViewModel,
                                                            ClickArtViewModel clickArtViewModel,
                                                            FileArtworkDataAccessObject fileArtworkDataAccessObject) {
        final FavoriteOutputBoundary favoriteOutputBoundary = new FavoritePresenter(cfrViewModel, viewManagerModel,
                clickArtViewModel, searchViewModel);
        final FavoriteInputBoundary favoriteInteractor = new FavoriteInteractor(fileArtworkDataAccessObject,
                favoriteOutputBoundary);
        return new FavoriteController(favoriteInteractor);
    }

    private static RatingController createRatingUseCase(ViewManagerModel viewManagerModel,
                                                            SearchViewModel searchViewModel,
                                                            CfrViewModel cfrViewModel,
                                                            ClickArtViewModel clickArtViewModel,
                                                            FileArtworkDataAccessObject fileArtworkDataAccessObject) {
        final RatingOutputBoundary ratingOutputBoundary = new RatingPresenter(cfrViewModel, viewManagerModel,
                clickArtViewModel, searchViewModel);
        final RatingInputBoundary ratingInteractor = new RatingInteractor(fileArtworkDataAccessObject,
                ratingOutputBoundary);
        return new RatingController(ratingInteractor);
    }

    private static CommentController createCommentUseCase(ViewManagerModel viewManagerModel,
                                                            SearchViewModel searchViewModel,
                                                            CfrViewModel cfrViewModel,
                                                            ClickArtViewModel clickArtViewModel,
                                                            FileArtworkDataAccessObject fileArtworkDataAccessObject) {
        final CommentOutputBoundary commentOutputBoundary = new CommentPresenter(cfrViewModel, viewManagerModel,
                clickArtViewModel, searchViewModel);
        final CommentInputBoundary commentInteractor = new CommentInteractor(fileArtworkDataAccessObject,
                commentOutputBoundary);
        return new CommentController(commentInteractor);
    }

    private static SearchController createSearchUseCase(ViewManagerModel viewManagerModel,
                                                        SearchViewModel searchViewModel,
                                                        MuseumDataAccessObject museumDataAccessObject,
                                                        ClickArtViewModel clickArtViewModel) {
        final SearchOutputBoundary searchOutputBoundary = new SearchPresenter(viewManagerModel,
                searchViewModel, clickArtViewModel);
        final SearchInputBoundary searchInteractor = new SearchInteractor(museumDataAccessObject,
                searchOutputBoundary);

        return new SearchController(searchInteractor);
    }
}
