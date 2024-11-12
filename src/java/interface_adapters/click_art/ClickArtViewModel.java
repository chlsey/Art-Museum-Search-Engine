package interface_adapters.click_art;

import interface_adapters.ViewModel;

public class ClickArtViewModel extends ViewModel<ClickArtState> {
    public ClickArtViewModel() {
        super("logged in");
        setState(new ClickArtState());
    }
}
