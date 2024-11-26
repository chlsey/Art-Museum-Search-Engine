package use_case.filter;

public class FilterInputData {
    private final String specification;

    public FilterInputData(String spec) {
        this.specification = spec;
    }

    String getCurrentFilter() {
        return this.specification;
    }
}
