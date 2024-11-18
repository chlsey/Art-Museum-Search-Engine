package interface_adapters.click_art;

import entities.Artwork;
import interface_adapters.ViewModel;

public class ClickArtViewModel extends ViewModel<ClickArtState> {
    public static final String STATE_CHANGED_PROPERTY = "stateChanged";

    private Artwork selectedArtwork;
    public ClickArtViewModel() {
        super("clickView");
        setState(new ClickArtState());
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
