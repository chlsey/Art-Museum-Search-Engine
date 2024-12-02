package view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import javax.swing.*;

import entities.Artwork;
import interface_adapters.CfrViewModel;
import interface_adapters.click_art.ClickArtViewModel;
import interface_adapters.comment.CommentController;
import interface_adapters.favorite.FavoriteController;
import interface_adapters.rating.RatingController;

/**
 * Crf view.
 */
public class CfrView extends JPanel implements PropertyChangeListener {

    private static final int SIZE = 20;
    private static final int INDEX = 5;
    private static final int SIZE_24 = 24;
    private static final String ARIAL = "Arial";

    private final String viewName = "CFRView";
    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private final CfrViewModel cfrViewModel;
    private Artwork artwork;

    private final JTextField commentInput;
    private final JButton submitButton;
    private final JButton backButton;
    private final JLabel ratingLabel;
    private final JLabel favoriteLabel;
    private final JButton favoriteButton;
    private int selectedRating = 0;
    private boolean isFavorited;

    private final RatingController ratingController;
    private final FavoriteController favoriteController;
    private final CommentController commentController;

    private final JButton[] stars;
    private final ImageIcon starEmptyIcon;
    private final ImageIcon starFilledIcon;
    private final ImageIcon heartEmptyIcon;
    private final ImageIcon heartFilledIcon;

    public CfrView(CfrViewModel cfrViewModel, ClickArtViewModel clickArtViewModel, RatingController ratingController,
                   FavoriteController favoriteController, CommentController commentController) {
        this.cfrViewModel = cfrViewModel;
        this.ratingController = ratingController;
        this.favoriteController = favoriteController;
        this.commentController = commentController;
        cfrViewModel.addPropertyChangeListener(this);

        // Load image icons
        final ImageIcon rawStarEmpty = new ImageIcon("src/images/star_empty.png");
        final ImageIcon rawStarFilled = new ImageIcon("src/images/star_filled.png");
        final ImageIcon rawHeartEmpty = new ImageIcon("src/images/heart_empty.png");
        final ImageIcon rawHeartFilled = new ImageIcon("src/images/heart_filled.png");
        final Image imageStarEmpty = rawStarEmpty.getImage();
        final Image imageStarFilled = rawStarFilled.getImage();
        final Image imageHeartEmpty = rawHeartEmpty.getImage();
        final Image imageHeartFilled = rawHeartFilled.getImage();
        final Image newStarEmpty = imageStarEmpty.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        final Image newStarFilled = imageStarFilled.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        final Image newHeartEmpty = imageHeartEmpty.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        final Image newHeartFilled = imageHeartFilled.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        starEmptyIcon = new ImageIcon(newStarEmpty);
        starFilledIcon = new ImageIcon(newStarFilled);
        heartEmptyIcon = new ImageIcon(newHeartEmpty);
        heartFilledIcon = new ImageIcon(newHeartFilled);

        setLayout(new BorderLayout());
        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);

        // Main Panel
        final JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(SIZE, SIZE, SIZE, SIZE));

        // Title
        final JLabel titleLabel = new JLabel("Rate, Favorite and Comment");
        titleLabel.setFont(new Font(ARIAL, Font.BOLD, SIZE_24));
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, SIZE)));

        // Rating Section
        final JPanel ratingPanel = new JPanel();
        ratingPanel.setLayout(new BoxLayout(ratingPanel, BoxLayout.Y_AXIS));
        ratingLabel = new JLabel("Rate This Work");
        ratingLabel.setFont(new Font(ARIAL, Font.BOLD, SIZE));
        ratingLabel.setAlignmentX(CENTER_ALIGNMENT);
        ratingPanel.add(ratingLabel);

        final JPanel starPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        stars = new JButton[INDEX];
        for (int i = 0; i < INDEX; i++) {
            final int ratingValue = i + 1;
            stars[i] = new JButton(starEmptyIcon);
            stars[i].setContentAreaFilled(false);
            stars[i].setBorderPainted(false);
            stars[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    setRating(ratingValue);
                }
            });
            starPanel.add(stars[i]);
        }
        ratingPanel.add(starPanel);
        contentPanel.add(ratingPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, SIZE)));

        // Favorite Section
        final JPanel favoritePanel = new JPanel();
        favoritePanel.setLayout(new BoxLayout(favoritePanel, BoxLayout.Y_AXIS));
        favoriteLabel = new JLabel("Favorite This Work");
        favoriteLabel.setFont(new Font(ARIAL, Font.BOLD, SIZE));
        favoriteLabel.setAlignmentX(CENTER_ALIGNMENT);
        favoritePanel.add(favoriteLabel);

        favoriteButton = new JButton(heartEmptyIcon);
        favoriteButton.setContentAreaFilled(false);
        favoriteButton.setBorderPainted(false);
        favoriteButton.addActionListener(event -> {
            try {
                toggleFavorite();
            }
            catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        final JPanel favoriteButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        favoriteButtonPanel.add(favoriteButton);
        favoritePanel.add(favoriteButtonPanel);

        contentPanel.add(favoritePanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, SIZE)));

        // Comment Section
        final JPanel commentPanel = new JPanel();
        commentPanel.setLayout(new BoxLayout(commentPanel, BoxLayout.Y_AXIS));
        final JLabel commentLabel = new JLabel("Your Comment:");
        commentLabel.setFont(new Font(ARIAL, Font.BOLD, SIZE));
        commentLabel.setAlignmentX(CENTER_ALIGNMENT);
        commentPanel.add(commentLabel);

        commentInput = new JTextField(SIZE);
        commentInput.setMaximumSize(new Dimension(Integer.MAX_VALUE, commentInput.getPreferredSize().height));
        commentPanel.add(commentInput);

        contentPanel.add(commentPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, SIZE)));

        // Buttons Section
        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        submitButton = new JButton("Submit");
        backButton = new JButton("Back");
        buttonPanel.add(submitButton);
        buttonPanel.add(backButton);
        contentPanel.add(buttonPanel);

        // Add functionality to buttons
        submitButton.addActionListener(event -> {
            try {
                submit();
            }
            catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        backButton.addActionListener(event -> {
            clickArtViewModel.setSelectedArtwork(artwork);
            clickArtViewModel.firePropertyChanged();

            final CardLayout cardLayout = (CardLayout) getParent().getLayout();
            cardLayout.show(getParent(), "ClickView");
        });

        // Add to Main Panel
        mainPanel.add(contentPanel, viewName);
        add(mainPanel, BorderLayout.CENTER);
    }

    private void setRating(int rating) {
        selectedRating = rating;
        for (int i = 0; i < stars.length; i++) {
            stars[i].setIcon(i < rating ? starFilledIcon : starEmptyIcon);
        }
    }

    private void toggleFavorite() throws IOException {
        isFavorited = !isFavorited;
        favoriteButton.setIcon(isFavorited ? heartFilledIcon : heartEmptyIcon);
        artwork.setFavorited(isFavorited);
        favoriteController.execute(artwork);

        cfrViewModel.firePropertyChanged();
    }

    private void submit() throws IOException {
        final String newComment = commentInput.getText().trim();
        if (!newComment.isEmpty() || selectedRating > 0) {
            if (!newComment.isEmpty()) {
                artwork.addComment(newComment);
                commentController.execute(artwork, newComment);
            }
            if (selectedRating > 0) {
                artwork.setRating(selectedRating);
                ratingController.execute(artwork.getId(), selectedRating);
            }
            cfrViewModel.firePropertyChanged();
            commentInput.setText("");
            JOptionPane.showMessageDialog(this, "Thank you for your feedback!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(this, "Please provide a rating or comment",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        artwork = cfrViewModel.getSelectedArtwork();
        if ("CFRView".equals(evt.getPropertyName())) {
            updateState(artwork);
            cardLayout.show(mainPanel, viewName);
        }
        revalidate();
        repaint();
    }

    private void updateState(Artwork artwork) {
        if (artwork != null) {
            final int artRating = artwork.getRating();
            showRating(artRating);

            isFavorited = artwork.checkFavorited();
            favoriteButton.setIcon(isFavorited ? heartFilledIcon : heartEmptyIcon);
        }
        else {
            showRating(0);
            isFavorited = false;
            favoriteButton.setIcon(heartEmptyIcon);
        }
    }

    private void showRating(int artRating) {
        for (int i = 0; i < stars.length; i++) {
            if (i < artRating) {
                stars[i].setIcon(starFilledIcon);
            }
            else {
                stars[i].setIcon(starEmptyIcon);
            }
        }
    }

    public String getViewName() {
        return viewName;
    }
}
