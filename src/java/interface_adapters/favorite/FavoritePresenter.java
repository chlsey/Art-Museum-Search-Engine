package interface_adapters.favorite;

import interface_adapters.CFRViewModel;
import interface_adapters.ViewManagerModel;
import use_case.favorite.FavoriteOutputBoundary;
import use_case.favorite.FavoriteOutputData;

public class FavoritePresenter implements FavoriteOutputBoundary {

    private final CFRViewModel loginViewModel;
    private final CFRViewModel loggedInViewModel;
    private final ViewManagerModel viewManagerModel;

    public FavoritePresenter(CFRViewModel loginViewModel, CFRViewModel loggedInViewModel, ViewManagerModel viewManagerModel) {
        this.loginViewModel = loginViewModel;
        this.loggedInViewModel = loggedInViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(FavoriteOutputData outputData) {

    }

    @Override
    public void prepareFailView(String errorMessage) {

    }
}
