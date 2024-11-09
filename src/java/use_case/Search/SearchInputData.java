package use_case.Search;

public class SearchInputData {

    private final String searchMessage;

    public SearchInputData(String searchMessage) {
        this.searchMessage = searchMessage;
    }

    String getSearchMessage() {
        return this.searchMessage;
    }
}
