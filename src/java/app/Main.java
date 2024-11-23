package app;
import data.InMemoryDataAccessObject;
import data.MuseumDataAccessObject;
import entities.Artwork;
import interface_adapters.ViewManagerModel;
import interface_adapters.click_art.ClickArtController;
import interface_adapters.click_art.ClickArtPresenter;
import interface_adapters.click_art.ClickArtViewModel;
import interface_adapters.search.SearchViewModel;
import use_case.click_art.ClickArtInteractor;
import use_case.click_art.ClickArtOutputBoundary;
import use_case.search.SearchDataAccessInterface;
import view.ClickView;
import view.SearchView;
import view.ViewManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
        final JFrame frame = new JFrame("Museum Search Engine");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final CardLayout cardLayout = new CardLayout();
        final JPanel views = new JPanel(cardLayout);
        frame.add(views);

        final ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(views, cardLayout, viewManagerModel);

        final SearchViewModel searchViewModel = new SearchViewModel();
        final MuseumDataAccessObject museumDataAccessObject = new MuseumDataAccessObject();

        final ClickArtViewModel clickArtViewModel = new ClickArtViewModel();
        final SearchView searchView = SearchUseCaseFactory.create(viewManagerModel, searchViewModel, museumDataAccessObject, clickArtViewModel);
        final ClickArtOutputBoundary clickArtPresenter = new ClickArtPresenter(searchViewModel, clickArtViewModel,viewManagerModel);
        //final ClickArtController clickArtController = new ClickArtController(new ClickArtInteractor(museumDataAccessObject, clickArtPresenter));

        final ClickView clickView = ClickUseCaseFactory.create(viewManagerModel, searchViewModel, clickArtViewModel, museumDataAccessObject);


        views.add(searchView, searchView.getViewName());
        views.add(clickView, clickView.getViewName());
        viewManagerModel.setState(searchView.getViewName());
        viewManagerModel.firePropertyChanged();
        frame.pack();
        frame.setVisible(true);
    }
}


