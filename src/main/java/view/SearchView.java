package view;

import entities.Artwork;
import interface_adapters.click_art.ClickArtViewModel;
import interface_adapters.filter.FilterController;
import interface_adapters.search.SearchController;
import interface_adapters.search.SearchViewModel;

import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import entities.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
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
    private final FilterController filterController;
    private final ClickArtViewModel clickArtViewModel;

    private final JButton favoriteButton;
    private final JButton searchButton;
    private final JButton clearButton;
    private final JRadioButton artistButton;
    private final JRadioButton hasImageButton;
    private final JRadioButton highLightButton;
    private final JRadioButton onViewButton;
    private final JPanel panelPictures;
    private JScrollPane scrollPanelPictures;
    //private final JButton nextButton;
    //private final JButton detailButton;


    public SearchView(SearchController controller, SearchViewModel searchViewModel, ClickArtViewModel clickArtViewModel, FilterController filterController) {
        this.searchController = controller;
        this.searchViewModel = searchViewModel;
        this.clickArtViewModel = clickArtViewModel;
        this.filterController = filterController;
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
        favoriteButton = new JButton("Go To My Favorite Artworks");
        searchButton = new JButton("Search");
        clearButton = new JButton("Clear");

        // Radio Button
        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.X_AXIS));

        ButtonGroup group = new ButtonGroup();
        artistButton = new JRadioButton("Artist");
        hasImageButton = new JRadioButton("Has Image");
        highLightButton = new JRadioButton("High Light");
        onViewButton = new JRadioButton("On View");

        group.add(artistButton);
        group.add(hasImageButton);
        group.add(highLightButton);
        group.add(onViewButton);

        radioPanel.add(artistButton);
        radioPanel.add(hasImageButton);
        radioPanel.add(highLightButton);
        radioPanel.add(onViewButton);

        searchButton.addActionListener(e -> {
            String keyword = keywordInputField.getText();
            //System.out.println("Button clicked, keyword: " + keyword); // Debugging
            // Get spec from radiobutton
            String qual = "";
            if (artistButton.isSelected()) {
                qual = "Artist";
            }
            else if (hasImageButton.isSelected()) {
                qual = "Hasimages";
            }
            else if (highLightButton.isSelected()) {
                qual = "Highlight";
            }
            else if (onViewButton.isSelected()) {
                qual = "Onview";
            }
            else{qual = "Artist";}
            filterController.execute(qual);
            searchController.execute(keyword);
        });

        // The search will also work with enter(return key)
        keywordInputField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String keyword = keywordInputField.getText();
                    //System.out.println("Enter key pressed, keyword: " + keyword); // Debugging
                    searchController.execute(keyword);
                }
            }
        });



        clearButton.addActionListener(this);
        buttons.add(favoriteButton);
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
        this.add(radioPanel);
        this.add(buttons);
        JLabel searchResults = new JLabel("Results:");
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
            // Get the keyword from the input field and trigger the search
            String keyword = keywordInputField.getText();
            searchController.execute(keyword);

            // Get spec from radiobutton
            String qual = "";
            if (artistButton.isSelected()) {
                qual = "Artist";
            }
            else if (hasImageButton.isSelected()) {
                qual = "Hasimages";
            }
            else if (highLightButton.isSelected()) {
                qual = "Highlight";
            }
            else if (onViewButton.isSelected()) {
                qual = "Onview";
            }
            else{qual = "Artist";}
            filterController.execute(qual);

        } else if (e.getSource() == clearButton) {
            // Clear the input field and reset the panel
            keywordInputField.setText("");
            panelPictures.removeAll();
            revalidate();
            repaint();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("searched".equals(evt.getPropertyName())) {
            List<Artwork> artworks = clickArtViewModel.getArtworks();
            panelPictures.removeAll();

            for (Artwork art : artworks) {
                try {
                    // Validate and load the image safely
                    Image image = loadImageSafely(art.getImageUrl());

                    // Scale the image
                    Image newImg = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                    ImageIcon imageIcon = new ImageIcon(newImg);

                    JLabel imageLabel = new JLabel(imageIcon);
                    imageLabel.setPreferredSize(new Dimension(200, 200)); // Fix preferred size

                    final Artwork artwork = art;

                    // Add mouse listeners
                    imageLabel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            clickArtViewModel.setSelectedArtwork(artwork);
                            clickArtViewModel.firePropertyChanged();

                            CardLayout cardLayout = (CardLayout) getParent().getLayout();
                            cardLayout.show(getParent(), "ClickView");
                        }

                        @Override
                        public void mouseEntered(MouseEvent e) {
                            Image hoverImage = image.getScaledInstance(250, 250, Image.SCALE_SMOOTH);
                            imageLabel.setIcon(new ImageIcon(hoverImage));
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            Image normalImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                            imageLabel.setIcon(new ImageIcon(normalImage));
                        }
                    });

                    panelPictures.add(imageLabel);

                } catch (Exception ex) {
                    ex.printStackTrace();

                    // If loading fails, add a placeholder
                    JLabel placeholderLabel = new JLabel("Image not available");
                    placeholderLabel.setPreferredSize(new Dimension(200, 200));
                    placeholderLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    placeholderLabel.setVerticalAlignment(SwingConstants.CENTER);
                    panelPictures.add(placeholderLabel);
                }
            }

            panelPictures.revalidate();
            panelPictures.repaint();
        }
    }

    /**
     * Helper method to load an image safely. Falls back to a placeholder if loading fails.
     */
    private Image loadImageSafely(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            return ImageIO.read(url);
        } catch (Exception e) {
            // Log the error and return a placeholder image
            System.err.println("Failed to load image: " + imageUrl);
            try {
                return ImageIO.read(new File("src/images/noimg.png")); // Local placeholder
            } catch (IOException ex) {
                throw new RuntimeException("Placeholder image not found.");
            }
        }
    }



    public String getViewName() {
        return viewName;
    }
}