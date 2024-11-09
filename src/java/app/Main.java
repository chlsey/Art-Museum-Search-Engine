package app;
import data.MuseumDataAccessObject;
import entities.Artwork;
import interface_adapters.ViewManagerModel;
import interface_adapters.search.SearchViewModel;
import use_case.search.SearchDataAccessInterface;
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

        final SearchView searchView = SearchUseCaseFactory.create(viewManagerModel, searchViewModel, museumDataAccessObject);

        views.add(searchView);
        viewManagerModel.setState(searchView.getViewName());
        viewManagerModel.firePropertyChanged();
        frame.pack();
        frame.setVisible(true);

        //JLabel label = new JLabel("Hi there!");
        //JButton button = new JButton("click me");
        //JPanel panel = new JPanel();
        //panel.add(label);
        //panel.add(button);
        //frame.setSize(300, 200);
        //frame.setContentPane(panel);
        //frame.setVisible(true);
        //JPanel search = new JPanel();
        //search.add(new JLabel("art: "));
        //search.add(new JTextField(15));
        //JPanel buttonPanel = new JPanel();
        //buttonPanel.add(new JButton("go"));
        //buttonPanel.add(new JButton("cancel"));
        //JPanel mainPanel = new JPanel();
       // mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
       // mainPanel.add(search);
       // mainPanel.add(buttonPanel);
       // frame.setContentPane(mainPanel);
        //JTextField searchField = new JTextField(15);
        //JButton go = new JButton("go");
        //go.addActionListener(new ActionListener() {
            //public void actionPerformed(ActionEvent e) {
               // String searchText = searchField.getText();
            //}
        //});
    }
}
