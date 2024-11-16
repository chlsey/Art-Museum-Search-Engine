package use_case.search;

public class SearchInputData {

    private final String searchMessage;

    public SearchInputData(String searchMessage) {
        this.searchMessage = searchMessage;
    }

    String getSearchMessage() {
        return this.searchMessage;
    }
}
