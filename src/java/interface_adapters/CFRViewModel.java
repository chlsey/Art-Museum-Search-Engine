package interface_adapters;

import entities.Artwork;
import interface_adapters.click_art.ClickArtState;
import interface_adapters.comment.CommentState;
import interface_adapters.favorite.FavoriteState;
import interface_adapters.rating.RatingState;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class CFRViewModel extends ViewModel<CFRState>{
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private RatingState ratingState;
    private CommentState commentState;
    private FavoriteState favoriteState;
    private Artwork selectedArtwork;
    private final String viewName = "CFRView";

    public CFRViewModel() {
        super("CFRView");
        setState(new CFRState());
        this.commentState = null;
        this.favoriteState = new FavoriteState();
        this.ratingState = new RatingState();
    }

    public void setState(CFRState cfrState) {
        super.setState(cfrState);
        firePropertyChanged("CFRView");
    }

    public CommentState getCommentState() {
        return commentState;
    }

    public FavoriteState getFavoriteState() {
        return favoriteState;
    }

    public void setCommentState(CommentState commentState) {
        this.commentState = commentState;
    }

    public void setFavoriteState(FavoriteState favoriteState) { this.favoriteState = favoriteState; }

    public String getViewName() {
        return viewName;
    }


    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    public RatingState getRatingState() {
        return ratingState;
    }

    public void setSelectedArtwork(Artwork selectedArtwork) {
        this.selectedArtwork = selectedArtwork;
    }

    public Artwork getSelectedArtwork(){
        return selectedArtwork;
    }
}


