package use_case.search;

public class SearchInputData {

    private final String searchMessage;
    private final String filters;

    public SearchInputData(String searchMessage, String filters) {
        this.searchMessage = searchMessage;
        this.filters = filters;
    }

    String getSearchMessage() {
        return this.searchMessage;
    }

    String getFilters() { return this.filters; }
}
