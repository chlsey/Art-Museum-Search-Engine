package interface_adapters.filter;

import use_case.filter.FilterOutputBoundary;
import use_case.filter.FilterOutputData;

public class FilterPresenter implements FilterOutputBoundary {

    @Override
    public void prepareFailView(String errorMessage) {
        final FilterState searchState = FilterViewModel.getState();
        FilterViewModel.firePropertyChanged();
    }

    public void prepareSuccessView(FilterOutputData filterOutputData) {

    }
}
