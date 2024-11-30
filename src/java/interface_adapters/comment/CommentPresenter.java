package interface_adapters.comment;

import interface_adapters.CFRViewModel;
import interface_adapters.ViewManagerModel;
import use_case.comment.CommentOutputBoundary;
import use_case.comment.CommentOutputData;

public class CommentPresenter implements CommentOutputBoundary {
    private final CFRViewModel cfrViewModel;
    private final ViewManagerModel viewManagerModel;

    public CommentPresenter(CFRViewModel cfrViewModel, ViewManagerModel viewManagerModel) {
        this.cfrViewModel = cfrViewModel;
        this.viewManagerModel = viewManagerModel;
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

