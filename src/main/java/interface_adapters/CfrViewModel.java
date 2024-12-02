package interface_adapters;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import entities.Artwork;

/**
 * Cfr view model.
 */
public class CfrViewModel extends ViewModel<CfrState> {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private Artwork selectedArtwork;
    private final String viewName = "CFRView";

    public CfrViewModel() {
        super("CFRView");
        setState(new CfrState());
    }

    /**
     * Set State.
     * @param cfrState cfrState
     */
    public void setState(CfrState cfrState) {
        super.setState(cfrState);
        firePropertyChanged("CFRView");
    }

    public String getViewName() {
        return viewName;
    }

    /**
     * Remove property change listener.
     * @param listener listener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    public void setSelectedArtwork(Artwork selectedArtwork) {
        this.selectedArtwork = selectedArtwork;
    }

    public Artwork getSelectedArtwork() {
        return selectedArtwork;
    }
}
