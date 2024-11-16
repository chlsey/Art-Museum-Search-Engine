package interface_adapters;

import interface_adapters.comment.CommentState;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class CFRViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private CommentState commentState;
    private final String viewName = "CFRView";

    public CFRViewModel() {
        this.commentState = null;
    }

    public CommentState getState() {
        return commentState;
    }

    public void setState(CommentState commentState) {
        this.commentState = commentState;
    }

    public String getViewName() {
        return viewName;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    public void firePropertyChanged() {
        support.firePropertyChange("commentState", null, this.commentState);
    }
}


