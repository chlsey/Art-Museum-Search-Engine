package app;
import data.FileArtworkDataAccessObject;
import data.MuseumDataAccessObject;
import interface_adapters.CFRViewModel;
import interface_adapters.ViewManagerModel;
import interface_adapters.click_art.ClickArtViewModel;
import interface_adapters.search.SearchViewModel;
import view.CFRView;
import view.ClickView;
import view.SearchView;
import view.ViewManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        // Create the main JFrame
        final JFrame frame = new JFrame("Museum Search Engine");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set up CardLayout for view switching
        final CardLayout cardLayout = new CardLayout();
        final JPanel views = new JPanel(cardLayout);
        frame.setContentPane(views); // Add to frame as content pane

        // View manager model for controlling states
        final ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(views, cardLayout, viewManagerModel);

        // Search view setup
        final SearchViewModel searchViewModel = new SearchViewModel();
        final MuseumDataAccessObject museumDataAccessObject = new MuseumDataAccessObject();
        final FileArtworkDataAccessObject fileArtworkDataAccessObject = new FileArtworkDataAccessObject("src/favorite.json");
        final ClickArtViewModel clickArtViewModel = new ClickArtViewModel();
        final CFRViewModel cfrViewModel = new CFRViewModel();
        final SearchView searchView = SearchUseCaseFactory.create(viewManagerModel, searchViewModel, museumDataAccessObject, clickArtViewModel,fileArtworkDataAccessObject,cfrViewModel);

        // ClickArt view setup
        //final ClickArtPresenter clickArtPresenter = new ClickArtPresenter(searchViewModel, clickArtViewModel, viewManagerModel);
        final ClickView clickView = ClickUseCaseFactory.create(viewManagerModel, searchViewModel, cfrViewModel,clickArtViewModel, museumDataAccessObject);
        final CFRView cfrView = CFRUseCaseFactory.create(viewManagerModel, searchViewModel, cfrViewModel,clickArtViewModel, fileArtworkDataAccessObject);

        // Add views to CardLayout
        views.add(searchView, searchView.getViewName());
        views.add(clickView, clickView.getViewName());
        views.add(cfrView, cfrView.getViewName());

        // Set the initial state to SearchView
        viewManagerModel.setState(searchView.getViewName());
        viewManagerModel.firePropertyChanged();

        // Configure frame for full-screen mode
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize window
        frame.setSize(1024, 768);
        frame.setUndecorated(false); // Keep window decorations like title bar
        frame.setVisible(true);
    }
}


