package use_case.search;

/**
 * The input data of searching.
 */
public class SearchInputData {

    private final String searchMessage;

    public SearchInputData(String searchMessage) {
        this.searchMessage = searchMessage;
    }

    String getSearchMessage() {
        return this.searchMessage;
    }
}
