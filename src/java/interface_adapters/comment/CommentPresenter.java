package interface_adapters.comment;

import interface_adapters.CFRViewModel;
import interface_adapters.ViewManagerModel;
import interface_adapters.ViewModel;
import interface_adapters.click_art.ClickArtViewModel;
import interface_adapters.search.SearchViewModel;
import use_case.comment.CommentOutputBoundary;
import use_case.comment.CommentOutputData;
import view.CFRView;

import javax.swing.text.View;

public class CommentPresenter implements CommentOutputBoundary {
    private final CFRViewModel cfrViewModel;
    private final ViewManagerModel viewManagerModel;
    private final ClickArtViewModel clickArtViewModel;
    private final SearchViewModel searchViewModel;

    public CommentPresenter(CFRViewModel cfrViewModel, ViewManagerModel viewManagerModel,
                            ClickArtViewModel clickArtViewModel, SearchViewModel searchViewModel) {
        this.cfrViewModel = cfrViewModel;
        this.viewManagerModel = viewManagerModel;
        this.clickArtViewModel = clickArtViewModel;
        this.searchViewModel = searchViewModel;
    }

    @Override
    public void presentSuccessView(CommentOutputData outputData) {
        //final CommentState commentState = CFRViewModel.getState();
        //this.cfrViewModel.setState(commentState);
        this.cfrViewModel.firePropertyChanged();
        this.viewManagerModel.setState(cfrViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();

    }

    @Override
    public void presentFailureView(String errorMessage) {
        //final CommentState commentState = CFRViewModel.getState();
        //commentState.setCommentError(errorMessage);
        cfrViewModel.firePropertyChanged();
    }
}

