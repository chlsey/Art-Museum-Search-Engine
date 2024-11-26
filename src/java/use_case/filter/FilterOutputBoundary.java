package use_case.filter;

public interface FilterOutputBoundary {
    void prepareSuccessView(FilterOutputData data);
    void prepareFailView(String errorMessage);
}
