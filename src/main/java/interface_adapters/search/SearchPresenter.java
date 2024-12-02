package interface_adapters.search;

import entities.Artwork;
import interface_adapters.ViewManagerModel;
import interface_adapters.click_art.ClickArtState;
import interface_adapters.click_art.ClickArtViewModel;
import use_case.search.SearchOutputBoundary;
import use_case.search.SearchOutputData;

import java.util.List;

public class SearchPresenter implements SearchOutputBoundary {

    private final SearchViewModel searchViewModel;
    private final ViewManagerModel viewManagerModel;
    private final ClickArtViewModel clickArtViewModel;

    public SearchPresenter(ViewManagerModel viewManagerModel, SearchViewModel searchViewModel,
                           ClickArtViewModel clickArtViewModel) {
        this.searchViewModel = searchViewModel;
        this.viewManagerModel = viewManagerModel;
        this.clickArtViewModel = clickArtViewModel;
    }

    @Override
    public void prepareSuccessView(SearchOutputData outputData) {
        List<Artwork> ourArtworks = outputData.getArtworks();
        final ClickArtState clickArtState = clickArtViewModel.getState();
        clickArtState.setArtworks(ourArtworks);
        this.clickArtViewModel.setState(clickArtState);
        this.clickArtViewModel.firePropertyChanged();
        searchViewModel.setArtworks(ourArtworks);
        //searchViewModel.firePropertyChanged();

        this.viewManagerModel.setState(clickArtViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged("searched");

//        StringBuilder artworks = new StringBuilder();
//        JPanel panelPictures = new JPanel();
//        for (Artwork art: ourArtworks) {
//            artworks.append(art.getTitle() + "\n");
//            try {
//                URI newuri = new URI(art.getImageUrl());
//                // System.out.println(newuri);
//                ImageIcon imageIcon;
//                if (newuri.isAbsolute()) {
//                    imageIcon = new ImageIcon(newuri.toURL());
//                } else {
//                    imageIcon = new ImageIcon(art.getImageUrl());
//                } // load the image to a imageIcon
//                Image image = imageIcon.getImage(); // transform it
//                Image newimg = image.getScaledInstance(200, 200,  awt.Image.SCALE_SMOOTH); // scale it the smooth way
//                imageIcon = new ImageIcon(newimg);  // transform it back
//                JLabel imagelabel = new JLabel(imageIcon);
//                panelPictures.add(imagelabel);
//            } catch (URISyntaxException | MalformedURLException ew) {
//                throw new RuntimeException(ew);
//            }
//        }

//        final ClickArtState clickArtState =  clickArtViewModel.getState();
//        clickArtState.setArtworks(outputData.getArtworks());
//        this.clickArtViewModel.setState(clickArtState);
//        this.clickArtViewModel.firePropertyChanged();
//
//        this.viewManagerModel.firePropertyChanged();
//        this.viewManagerModel.setState(clickArtViewModel.getViewName());
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final SearchState searchState = searchViewModel.getState();
        searchViewModel.firePropertyChanged();
        this.viewManagerModel.setState(clickArtViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged("searched");
    }
}
