package java.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.interface_adapters.search.SearchController;
import java.util.List;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.interface_adapters.search.SearchViewModel;
import java.entities.Artwork;

/**
 * The View for the Search Use Case.
 */
public class SearchView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "search";

    private final SearchViewModel searchViewModel;
    private final JTextField keywordInputField = new JTextField(15);
    private final JTextField genreInputField = new JTextField(15);
    private final JTextField timePeriodInputField = new JTextField(15);
    private final JTextArea searchResultsArea = new JTextArea(10, 30);
    private final SearchController searchController;

    private final JButton searchButton;
    private final JButton clearButton;

    public SearchView(SearchController controller, SearchViewModel searchViewModel) {
        this.searchController = controller;
        this.searchViewModel = searchViewModel;
        searchViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("Search for Artworks");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Panel to input search criteria
        final JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        final LabelTextPanel keywordInfo = new LabelTextPanel(
                new JLabel("Keywords:"), keywordInputField);
        final LabelTextPanel genreInfo = new LabelTextPanel(
                new JLabel("Genre:"), genreInputField);
        final LabelTextPanel timePeriodInfo = new LabelTextPanel(
                new JLabel("Time Period:"), timePeriodInputField);

        inputPanel.add(keywordInfo);
        inputPanel.add(genreInfo);
        inputPanel.add(timePeriodInfo);

        // Panel for action buttons
        final JPanel buttons = new JPanel();
        searchButton = new JButton("Search");
        clearButton = new JButton("Clear");

        buttons.add(searchButton);
        buttons.add(clearButton);

        // Set up search results area
        searchResultsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(searchResultsArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Add action listeners
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                final String keyword = keywordInputField.getText();
                final String genre = genreInputField.getText();
                final String timePeriod = timePeriodInputField.getText();
                searchController.executeSearch(keyword, genre, timePeriod);
            }
        });

        clearButton.addActionListener(this);

        // Add listeners to input fields
        addKeywordListener();
        addGenreListener();
        addTimePeriodListener();

        // Arrange components in layout
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(inputPanel);
        this.add(buttons);
        this.add(new JLabel("Search Results:"));
        this.add(scrollPane);
    }

    private void addTimePeriodListener() {
    }

    private void addGenreListener() {
    }

    private void addKeywordListener() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}