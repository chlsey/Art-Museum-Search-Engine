package app;

import java.awt.*;
import java.io.IOException;

import javax.swing.*;

import data.FileArtworkDataAccessObject;
import data.MuseumDataAccessObject;
import interface_adapters.CfrViewModel;
import interface_adapters.ViewManagerModel;
import interface_adapters.click_art.ClickArtViewModel;
import interface_adapters.search.SearchViewModel;
import view.CfrView;
import view.ClickView;
import view.SearchView;
import view.ViewManager;

/**
 * Mian class.
 */
public class Main {
    /**
     * Mian.
     * @param args args
     * @throws IOException exception
     */
    public static void main(String[] args) throws IOException {

        final int width = 1024;
        final int height = 768;

        // Create the main JFrame
        final JFrame frame = new JFrame("Museum Search Engine");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set up CardLayout for view switching
        final CardLayout cardLayout = new CardLayout();
        final JPanel views = new JPanel(cardLayout);
        frame.setContentPane(views);

        // View manager model for controlling states
        final ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(views, cardLayout, viewManagerModel);

        // Search view setup
        final SearchViewModel searchViewModel = new SearchViewModel();
        final MuseumDataAccessObject museumDataAccessObject = new MuseumDataAccessObject();
        final FileArtworkDataAccessObject fileArtworkDataAccessObject =
                new FileArtworkDataAccessObject("src/favorite.json");
        final ClickArtViewModel clickArtViewModel = new ClickArtViewModel();
        final CfrViewModel cfrViewModel = new CfrViewModel();
        final SearchView searchView = SearchUseCaseFactory.create(viewManagerModel,
                searchViewModel, museumDataAccessObject, clickArtViewModel, fileArtworkDataAccessObject, cfrViewModel);

        final ClickView clickView = ClickUseCaseFactory.create(viewManagerModel, searchViewModel,
                cfrViewModel, clickArtViewModel, museumDataAccessObject);
        final CfrView cfrView = CfrUseCaseFactory.create(viewManagerModel, searchViewModel, cfrViewModel,
                clickArtViewModel, fileArtworkDataAccessObject);

        // Add views to CardLayout
        views.add(searchView, searchView.getViewName());
        views.add(clickView, clickView.getViewName());
        views.add(cfrView, cfrView.getViewName());

        // Set the initial state to SearchView
        viewManagerModel.setState(searchView.getViewName());
        viewManagerModel.firePropertyChanged();

        // Configure frame for full-screen mode
        frame.setSize(width, height);
        frame.setUndecorated(false);
        frame.setVisible(true);
    }
}
