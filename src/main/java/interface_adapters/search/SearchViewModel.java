package interface_adapters.search;

import java.util.List;

import entities.Artwork;
import interface_adapters.ViewModel;

/**
 * Search view model.
 */
public class SearchViewModel extends ViewModel<SearchState> {
    public static final String TITLE_LABLE = "Search View";
    public static final String SEARCH_BUTTON_LABEL = "Search";
    public static final String CLEAR_BUTTON_LABEL = "Clear";
    public static final String STATE_CHANGED_PROPERTY = "searched";

    public SearchViewModel() {
        super("search");
        setState(new SearchState());
    }

    /**
     * Set artwork.
     * @param artworks artwork
     */
    public void setArtworks(List<Artwork> artworks) {
        this.getState().setArtworks(artworks);
        firePropertyChanged(STATE_CHANGED_PROPERTY);
    }

    public List<Artwork> getArtworks() {
        return this.getState().getArtworks();
    }
}
