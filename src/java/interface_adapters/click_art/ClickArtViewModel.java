package interface_adapters.click_art;

import entities.Artwork;
import interface_adapters.ViewModel;

import java.util.List;

public class ClickArtViewModel extends ViewModel<ClickArtState> {
    public static final String STATE_CHANGED_PROPERTY = "click";
    private List<Artwork> favorites;

    private List<Artwork> artworks;
    private Artwork selectedArtwork;
    public ClickArtViewModel() {
        super("clickView");
        setState(new ClickArtState());

    }

    public List<Artwork> getFavoriteArtworks() {
        return favorites;
    }

    private void setFavorites(List<Artwork> favoriteArtworks) {
        this.favorites = favoriteArtworks;
    }

    public List<Artwork> getArtworks() {
        return this.getState().getArtworks();
    }

    public Artwork getSelectedArtwork() {
        return selectedArtwork;
    }

    public void setSelectedArtwork(Artwork selectedArtwork) {
        this.selectedArtwork = selectedArtwork;
        firePropertyChanged(STATE_CHANGED_PROPERTY); // Notify listeners of state change
    }

    @Override
    public void setState(ClickArtState state) {
        super.setState(state);
        firePropertyChanged(STATE_CHANGED_PROPERTY);
    }
}
