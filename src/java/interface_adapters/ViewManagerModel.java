package interface_adapters;

public class ViewManagerModel extends ViewModel<String> {

    public ViewManagerModel() {
        super("search");
        this.setState("");
    }
}
