package java.interface_adapters.search;

import java.interface_adapters.ViewModel;

public class SearchViewModel extends ViewModel<SearchState> {

    public SearchViewModel() {
        super("search");
        setState(new SearchState());
    }
}
