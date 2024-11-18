package interface_adapters.search;

import interface_adapters.ViewModel;

public class SearchViewModel extends ViewModel<SearchState> {

    public static final String TITLE_LABLE = "Search View";

    public static final String SEARCH_BUTTON_LABEL = "Search";
    public static final String CLEAR_BUTTON_LABEL = "Clear";
    public static final String STATE_CHANGED_PROPERTY ="" ;

    public SearchViewModel() {
        super("search");
        setState(new SearchState());
    }
}
