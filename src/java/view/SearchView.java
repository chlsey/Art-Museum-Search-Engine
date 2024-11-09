package view;

import interface_adapters.search.SearchController;
import interface_adapters.search.SearchViewModel;

import java.util.List;
import entities.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static use_case.search.SearchInteractor.searchArtwork;

/**
 * The View for the Search Use Case.
 */
public class SearchView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "search";

    private final SearchViewModel searchViewModel;
    private final JTextField keywordInputField = new JTextField(15);
    private final JTextArea searchResultsArea = new JTextArea(10, 30);
    private final SearchController searchController;

    private final JButton searchButton;
    private final JButton clearButton;


    public SearchView(SearchController controller, SearchViewModel searchViewModel) {
        this.searchController = controller;
        this.searchViewModel = searchViewModel;
        searchViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel(SearchViewModel.SEARCH_BUTTON_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        final LabelTextPanel keywordInfo = new LabelTextPanel(
                new JLabel("Keywords:"), keywordInputField);

        inputPanel.add(keywordInfo);

        // Panel for action buttons
        final JPanel buttons = new JPanel();
        searchButton = new JButton("Search");
        clearButton = new JButton("Clear");
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String searchword = keywordInputField.getText();
                List<Artwork> all = searchArtwork(searchword);
                StringBuilder artworks = new StringBuilder();
                for (Artwork art: all) {
                    artworks.append(art.getTitle());
                }
                 searchResultsArea.setText(artworks.toString());
            }
        });
        buttons.add(searchButton);
        buttons.add(clearButton);

        // Set up search results area
        searchResultsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(searchResultsArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Add action listeners
        /**
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                final String keyword = keywordInputField.getText();
                searchController.executeSearch(keyword);
            }
        });
         */

        clearButton.addActionListener(this);

        // Add listeners to input fields
        addKeywordListener();

        // Arrange components in layout
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(inputPanel);
        this.add(buttons);
        this.add(new JLabel("Search Results:"));
        this.add(scrollPane);
    }


    private void addKeywordListener() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

    public String getViewName() {
        return viewName;
    }
}