package use_case.filter;

public class FilterInteractor implements FilterInputBoundary {

    private final FilterDataAccessInterface filterDataAccessObject;
    private final FilterOutputBoundary filterPresenter;

    public FilterInteractor(FilterDataAccessInterface filterDataAccessObject, FilterOutputBoundary filterPresenter) {
        this.filterDataAccessObject = filterDataAccessObject;
        this.filterPresenter = filterPresenter;
    }

    @Override
    public void execute(FilterInputData filterInputData) {
        final String filter = filterInputData.getCurrentFilter();
        filterDataAccessObject.changeFilter(filter);
        final FilterOutputData filterOutputData = new FilterOutputData(filter,false);
        filterPresenter.setFilter(filterOutputData);
    }
}