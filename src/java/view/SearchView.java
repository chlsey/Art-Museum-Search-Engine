package view;

import interface_adapters.click_art.ClickArtViewModel;
import interface_adapters.search.SearchController;
import interface_adapters.search.SearchState;
import interface_adapters.search.SearchViewModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import entities.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The View for the Search Use Case.
 */
public class SearchView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "search";

    private final SearchViewModel searchViewModel;
    private final JTextField keywordInputField = new JTextField(15);
    private final JTextArea searchResultsArea = new JTextArea(10, 30);
    private final SearchController searchController;
    private final ClickArtViewModel clickArtViewModel;

    private final JButton searchButton;
    private final JButton clearButton;
    private final JPanel panelPictures;
    private JScrollPane scrollPanelPictures;
    //private final JButton nextButton;
    //private final JButton detailButton;


    public SearchView(SearchController controller, SearchViewModel searchViewModel, ClickArtViewModel clickArtViewModel) {
        this.searchController = controller;
        this.searchViewModel = searchViewModel;
        this.clickArtViewModel = clickArtViewModel;
        //this.nextButton = nextButton;
        //this.detailButton = detailButton;
        searchViewModel.addPropertyChangeListener(this);

        final JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        final LabelTextPanel keywordInfo = new LabelTextPanel(
                new JLabel("Search Keywords:"), keywordInputField);

        keywordInfo.setAlignmentX(Component.CENTER_ALIGNMENT);

        inputPanel.add(keywordInfo);
        panelPictures = new JPanel();
        panelPictures.setLayout(new GridLayout(0, 7, 10, 10));
        scrollPanelPictures = new JScrollPane(panelPictures);
        scrollPanelPictures.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPanelPictures.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        // Panel for action buttons
        final JPanel buttons = new JPanel();
        searchButton = new JButton("Search");
        clearButton = new JButton("Clear");
        searchButton.addActionListener(e -> {
            String keyword = keywordInputField.getText();
            searchController.execute(keyword);
        });

        clearButton.addActionListener(this);
        buttons.add(searchButton);
        buttons.add(clearButton);
//        searchButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                // clear previous search results
//                if (!(panelPictures.getComponents() == null)) {
//                    panelPictures.removeAll();
//                }
//                String searchword = keywordInputField.getText();
//                final SearchState currentState = searchViewModel.getState();
//                currentState.setKeywords(searchword);
//                searchController.execute(
//                        currentState.getKeywords()
//                );

//                String searchword = keywordInputField.getText();
//                List<Artwork> all = searchArtwork(searchword, "Hasimages");
//                StringBuilder artworks = new StringBuilder();
//                for (Artwork art: all) {
//                    artworks.append(art.getTitle() + "\n");
//                    try {
//                        URI newuri = new URI(art.getImageUrl());
//                        // System.out.println(newuri);
//                        ImageIcon imageIcon;
//                        if (newuri.isAbsolute()) {
//                            imageIcon = new ImageIcon(newuri.toURL());
//                        } else {
//                            imageIcon = new ImageIcon(art.getImageUrl());
//                        } // load the image to a imageIcon
//                        Image image = imageIcon.getImage(); // transform it
//                        Image newimg = image.getScaledInstance(200, 200,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
//                        imageIcon = new ImageIcon(newimg);  // transform it back
//                        JLabel imagelabel = new JLabel(imageIcon);
//                        // Add a mouse listener to the image label for clicks
//                        final Artwork artwork = art; // Final reference for use in the mouse listener
//                        imagelabel.addMouseListener(new MouseAdapter() {
//                            @Override
//                            public void mouseClicked(MouseEvent e) {
//                                // When image is clicked, set selected artwork and switch to ClickView
//                                clickArtViewModel.setSelectedArtwork(artwork);
//                                clickArtViewModel.firePropertyChanged();
//                                // Switch to ClickView
//                                // This assumes you are using CardLayout for view switching
//                                CardLayout cardLayout = (CardLayout) getParent().getLayout();
//                                cardLayout.show(getParent(), "ClickView");
//                            }
//                        });
//                        panelPictures.add(imagelabel);
//                    } catch (URISyntaxException | MalformedURLException ew) {
//                        throw new RuntimeException(ew);
//                    }
//                }
//                repaint();
//                revalidate();
//            }
//        });


        buttons.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Set up search results area
        searchResultsArea.setEditable(false);
        searchResultsArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        /**
        JScrollPane scrollPane = new JScrollPane(searchResultsArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
         */

        // Add action listeners
        /**
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                final String keyword = keywordInputField.getText();
                searchController.executeSearch(keyword);
            }
        });
         */


        // Add listeners to input fields
//        addKeywordListener();

        // Arrange components in layout
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(inputPanel);
        this.add(buttons);
        JLabel searchResults = new JLabel("Search Results:");
        searchResults.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(searchResults);
        // this.add(scrollPane);
        this.add(scrollPanelPictures);
    }


    private void addKeywordListener() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            String keyword = keywordInputField.getText();
            searchController.execute(keyword);
        } else if (e.getSource() == clearButton) {
            keywordInputField.setText("");
            panelPictures.removeAll();
            revalidate();
            repaint();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        //final SearchState state = (SearchState) evt.getSource();
        //System.out.println(evt.getPropertyName());
        if ("searched".equals(evt.getPropertyName())) {
            List<Artwork> artworks = clickArtViewModel.getArtworks();
            panelPictures.removeAll();
            for (Artwork art : artworks) {
                try {
                    URI newuri = new URI(art.getImageUrl());
                    ImageIcon imageIcon;
                    if (newuri.isAbsolute()) {
                        imageIcon = new ImageIcon(newuri.toURL());
                    } else {
                        imageIcon = new ImageIcon(art.getImageUrl());
                    } // load the image to an imageIcon

                    Image image = imageIcon.getImage(); // transform it
                    Image newimg = image.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
                    imageIcon = new ImageIcon(newimg);  // transform it back

                    JLabel imagelabel = new JLabel(imageIcon);
                    imagelabel.setPreferredSize(new Dimension(200, 200)); // Fix the preferred size to prevent resizing

                    // Add a mouse listener to the image label for hover and click
                    final Artwork artwork = art; // Final reference for use in the mouse listener

                    imagelabel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            // When image is clicked, set selected artwork and switch to ClickView
                            clickArtViewModel.setSelectedArtwork(artwork);
                            clickArtViewModel.firePropertyChanged();

                            // Switch to ClickView
                            CardLayout cardLayout = (CardLayout) getParent().getLayout();
                            cardLayout.show(getParent(), "ClickView");
                        }

                        @Override
                        public void mouseEntered(MouseEvent e) {
                            // On hover, increase the size of the image
                            Image hoverImage = image.getScaledInstance(250, 250, java.awt.Image.SCALE_SMOOTH);
                            ImageIcon hoverIcon = new ImageIcon(hoverImage);
                            imagelabel.setIcon(hoverIcon); // Set the new hover icon
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            // On exit, reset the image to its original size
                            Image normalImage = image.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
                            ImageIcon normalIcon = new ImageIcon(normalImage);
                            imagelabel.setIcon(normalIcon); // Reset the original icon
                        }
                    });
                    panelPictures.add(imagelabel);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            panelPictures.revalidate();
            panelPictures.repaint();

            revalidate();
            repaint();
        }

    }

    public String getViewName() {
        return viewName;
    }
}