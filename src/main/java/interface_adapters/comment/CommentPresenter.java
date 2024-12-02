package interface_adapters.comment;

import interface_adapters.CfrViewModel;
import interface_adapters.ViewManagerModel;
import interface_adapters.click_art.ClickArtViewModel;
import interface_adapters.search.SearchViewModel;
import use_case.comment.CommentOutputBoundary;
import use_case.comment.CommentOutputData;

/**
 * Comment Presenter class.
 */
public class CommentPresenter implements CommentOutputBoundary {
    private final CfrViewModel cfrViewModel;
    private final ViewManagerModel viewManagerModel;
    private final ClickArtViewModel clickArtViewModel;
    private final SearchViewModel searchViewModel;

    public CommentPresenter(CfrViewModel cfrViewModel, ViewManagerModel viewManagerModel,
                            ClickArtViewModel clickArtViewModel, SearchViewModel searchViewModel) {
        this.cfrViewModel = cfrViewModel;
        this.viewManagerModel = viewManagerModel;
        this.clickArtViewModel = clickArtViewModel;
        this.searchViewModel = searchViewModel;
    }

    @Override
    public void presentSuccessView(CommentOutputData outputData) {
        this.cfrViewModel.firePropertyChanged();
        this.viewManagerModel.setState(cfrViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();

    }

    @Override
    public void presentFailureView(String errorMessage) {
        cfrViewModel.firePropertyChanged();
    }
}
