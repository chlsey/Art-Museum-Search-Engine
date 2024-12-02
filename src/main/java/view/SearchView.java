package view;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

import entities.Artwork;
import interface_adapters.click_art.ClickArtViewModel;
import interface_adapters.comment.CommentController;
import interface_adapters.favorite.FavoriteController;
import interface_adapters.filter.FilterController;
import interface_adapters.rating.RatingController;
import interface_adapters.search.SearchController;
import interface_adapters.search.SearchViewModel;

/**
 * The View for the Search Use Case.
 */
public class SearchView extends JPanel implements ActionListener, PropertyChangeListener {
    private static final int HUNDRED = 100;
    private static final int TWO_HUNDRED = 200;
    private static final int TWO_HUNDRED_FIFTY = 250;
    private static final int TWO_HUNDRED_THIRTY = 230;
    private static final int TEN = 10;
    private static final int SEVEN = 7;
    private static final int THIRTY = 30;
    private static final String ARTIST = "Artist";

    private final String viewName = "search";

    private final SearchViewModel searchViewModel;
    private final JTextField keywordInputField = new JTextField(15);
    private final JTextArea searchResultsArea = new JTextArea(10, 30);
    private final SearchController searchController;
    private final FilterController filterController;
    private final FavoriteController favoriteController;
    private final RatingController ratingController;
    private final CommentController commentController;
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

    public SearchView(SearchController controller, SearchViewModel searchViewModel,
                      ClickArtViewModel clickArtViewModel, FilterController filterController,
                      FavoriteController favoriteController, RatingController ratingController,
                      CommentController commentController) {
        this.searchController = controller;
        this.searchViewModel = searchViewModel;
        this.clickArtViewModel = clickArtViewModel;
        this.filterController = filterController;
        this.favoriteController = favoriteController;
        this.ratingController = ratingController;
        this.commentController = commentController;
        searchViewModel.addPropertyChangeListener(this);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        final JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        final LabelTextPanel keywordInfo = new LabelTextPanel(
                new JLabel("Search Keywords:"), keywordInputField);

        keywordInfo.setAlignmentX(Component.CENTER_ALIGNMENT);

        inputPanel.add(keywordInfo);
        panelPictures = new JPanel();
        panelPictures.setLayout(new GridLayout(0, SEVEN, TEN, TEN));
        scrollPanelPictures = new JScrollPane(panelPictures);
        scrollPanelPictures.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPanelPictures.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        final JPanel buttons = new JPanel();
        favoriteButton = new JButton("Go To My Favorite Artworks");
        searchButton = new JButton("Search");
        clearButton = new JButton("Clear");

        final JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.X_AXIS));
        radioPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        final ButtonGroup group = new ButtonGroup();
        artistButton = new JRadioButton(ARTIST);
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

        searchButton.addActionListener(event -> {
            final String keyword = keywordInputField.getText();
            String qual = "";
            if (artistButton.isSelected()) {
                qual = ARTIST;
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
            else {
                qual = ARTIST;
            }
            filterController.execute(qual);
            searchController.execute(keyword);
        });

        favoriteButton.addActionListener(event -> {
            favoriteController.goToFavorite();
        });

        keywordInputField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.VK_ENTER) {
                    final String keyword = keywordInputField.getText();
                    searchController.execute(keyword);
                }
            }
        });

        clearButton.addActionListener(this);
        buttons.add(favoriteButton);
        buttons.add(Box.createRigidArea(new Dimension(HUNDRED, TEN)));
        buttons.add(searchButton);
        buttons.add(clearButton);

        buttons.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchResultsArea.setEditable(false);
        searchResultsArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(inputPanel);
        this.add(radioPanel);
        this.add(buttons);
        final JLabel searchResults = new JLabel("Results:");
        searchResults.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(searchResults);
        this.add(scrollPanelPictures);
    }

    private void addKeywordListener() {
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == searchButton) {
            final String keyword = keywordInputField.getText();
            searchController.execute(keyword);
            String qual = "";
            if (artistButton.isSelected()) {
                qual = ARTIST;
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

            else {
                qual = ARTIST;
            }
            filterController.execute(qual);

        }
        else if (event.getSource() == clearButton) {
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
            final List<Artwork> artworks = clickArtViewModel.getArtworks();
            final List<Artwork> favoriteArtworks = favoriteController.getFavorites();
            final List<Artwork> hasRatingArtworks = ratingController.getRatedArtworks();
            final List<Artwork> hasCommentArtworks = commentController.getCommentedArtworks();
            panelPictures.removeAll();

            for (Artwork art : artworks) {
                for (Artwork favorite : favoriteArtworks) {
                    if (favorite.getId().replace("MET-", "")
                            .equals(art.getId().replace("MET-", ""))) {
                        art.setFavorited(true);
                        break;
                    }
                }
                for (Artwork hasRating : hasRatingArtworks) {
                    if (hasRating.getId().replace("MET-", "")
                            .equals(art.getId().replace("MET-", ""))) {
                        art.setRating(hasRating.getRating());
                        break;
                    }
                }
                for (Artwork hasComment : hasCommentArtworks) {
                    if (hasComment.getId().replace("MET-", "")
                            .equals(art.getId().replace("MET-", ""))) {
                        art.setComments(hasComment.getComments());
                        break;
                    }
                }
                try {
                    final Image image = loadImageSafely(art.getImageUrl());
                    final Image newImg = image.getScaledInstance(TWO_HUNDRED, TWO_HUNDRED, Image.SCALE_SMOOTH);
                    final ImageIcon imageIcon = new ImageIcon(newImg);

                    final JLabel imageLabel = new JLabel(imageIcon);
                    imageLabel.setPreferredSize(new Dimension(TWO_HUNDRED, TWO_HUNDRED));

                    final JPanel titleBar = new JPanel();
                    titleBar.setBackground(Color.LIGHT_GRAY);
                    final JLabel titleLabel = new JLabel(art.getTitle());
                    titleBar.add(titleLabel);
                    titleBar.setPreferredSize(new Dimension(TWO_HUNDRED, THIRTY));
                    titleBar.setVisible(false);

                    final JPanel imageContainer = new JPanel();
                    imageContainer.setLayout(new BorderLayout());
                    imageContainer.setPreferredSize(new Dimension(TWO_HUNDRED, TWO_HUNDRED_THIRTY));
                    imageContainer.add(imageLabel, BorderLayout.CENTER);
                    imageContainer.add(titleBar, BorderLayout.SOUTH);

                    final Artwork artwork = art;

                    imageLabel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent event) {
                            clickArtViewModel.setSelectedArtwork(artwork);
                            clickArtViewModel.firePropertyChanged();

                            final CardLayout cardLayout = (CardLayout) getParent().getLayout();
                            cardLayout.show(getParent(), "ClickView");
                        }

                        @Override
                        public void mouseEntered(MouseEvent event) {
                            final Image hoverImage = image.getScaledInstance(TWO_HUNDRED_FIFTY, TWO_HUNDRED_FIFTY,
                                    Image.SCALE_SMOOTH);
                            imageLabel.setIcon(new ImageIcon(hoverImage));
                            titleBar.setVisible(true);
                            titleBar.revalidate();
                            titleBar.repaint();
                        }

                        @Override
                        public void mouseExited(MouseEvent event) {
                            final Image normalImage = image.getScaledInstance(TWO_HUNDRED,
                                    TWO_HUNDRED, Image.SCALE_SMOOTH);
                            imageLabel.setIcon(new ImageIcon(normalImage));
                            titleBar.setVisible(false);
                            titleBar.revalidate();
                            titleBar.repaint();
                        }
                    });

                    panelPictures.add(imageContainer);

                }
                catch (Exception ex) {
                    ex.printStackTrace();
                    final JLabel placeholderLabel = new JLabel("Image not available");
                    placeholderLabel.setPreferredSize(new Dimension(TWO_HUNDRED, TWO_HUNDRED));
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
     * Load image safely.
     * @param imageUrl image url
     * @return exception
     */
    private Image loadImageSafely(String imageUrl) {
        try {
            final URL url = new URL(imageUrl);
            return ImageIO.read(url);
        }
        catch (Exception event) {
            System.err.println("Failed to load image: " + imageUrl);
            try {
                return ImageIO.read(new File("src/images/noimg.png"));
            }
            catch (IOException ex) {
                throw new RuntimeException("Placeholder image not found.");
            }
        }
    }

    public String getViewName() {
        return viewName;
    }
}
