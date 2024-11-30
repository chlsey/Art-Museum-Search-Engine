package view;

import entities.Artwork;
import interface_adapters.CFRViewModel;
import interface_adapters.click_art.ClickArtViewModel;
import interface_adapters.comment.CommentController;
import interface_adapters.favorite.FavoriteController;
import interface_adapters.rating.RatingController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class CFRView extends JPanel implements PropertyChangeListener {
    private final String viewName = "CFRView";
    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private final CFRViewModel cfrViewModel;
    private Artwork artwork;

    private final JTextField commentInput;
    private final JButton submitButton;
    private final JButton backButton;
    private final JLabel ratingLabel;
    private final JLabel favoriteLabel;
    private final JButton favoriteButton;
    private int selectedRating = 0; // Holds the user's selected rating
    private boolean isFavorited; // Tracks the favorite state

    private final RatingController ratingController;
    private final FavoriteController favoriteController;
    private final CommentController commentController;

    private final JButton[] stars;
    private final ImageIcon starEmptyIcon;
    private final ImageIcon starFilledIcon;
    private final ImageIcon heartEmptyIcon;
    private final ImageIcon heartFilledIcon;

    public CFRView(CFRViewModel cfrViewModel, ClickArtViewModel clickArtViewModel, RatingController ratingController, FavoriteController favoriteController, CommentController commentController) {
        this.cfrViewModel = cfrViewModel;
        this.ratingController = ratingController;
        this.favoriteController = favoriteController;
        this.commentController = commentController;
        cfrViewModel.addPropertyChangeListener(this);

        // Load image icons
        ImageIcon rawStarEmpty = new ImageIcon("src/images/star_empty.png");
        ImageIcon rawStarFilled = new ImageIcon("src/images/star_filled.png");
        ImageIcon rawHeartEmpty = new ImageIcon("src/images/heart_empty.png");
        ImageIcon rawHeartFilled = new ImageIcon("src/images/heart_filled.png");
        Image imageStarEmpty = rawStarEmpty.getImage();
        Image imageStarFilled = rawStarFilled.getImage();
        Image imageHeartEmpty = rawHeartEmpty.getImage();
        Image imageHeartFilled = rawHeartFilled.getImage();
        Image newStarEmpty = imageStarEmpty.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        Image newStarFilled = imageStarFilled.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        Image newHeartEmpty = imageHeartEmpty.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        Image newHeartFilled = imageHeartFilled.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        starEmptyIcon = new ImageIcon(newStarEmpty);
        starFilledIcon = new ImageIcon(newStarFilled);
        heartEmptyIcon = new ImageIcon(newHeartEmpty);
        heartFilledIcon = new ImageIcon(newHeartFilled);

        setLayout(new BorderLayout());
        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);

        // Main Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("Rate, Favorite and Comment");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Rating Section
        JPanel ratingPanel = new JPanel();
        ratingPanel.setLayout(new BoxLayout(ratingPanel, BoxLayout.Y_AXIS));
        ratingLabel = new JLabel("Rate This Work");
        ratingLabel.setFont(new Font("Arial", Font.BOLD, 20));
        ratingLabel.setAlignmentX(CENTER_ALIGNMENT);
        ratingPanel.add(ratingLabel);

        JPanel starPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        stars = new JButton[5];
        for (int i = 0; i < 5; i++) {
            int ratingValue = i + 1; // Rating is 1-based
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
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Favorite Section
        JPanel favoritePanel = new JPanel();
        favoritePanel.setLayout(new BoxLayout(favoritePanel, BoxLayout.Y_AXIS));
        favoriteLabel = new JLabel("Favorite This Work");
        favoriteLabel.setFont(new Font("Arial", Font.BOLD, 20));
        favoriteLabel.setAlignmentX(CENTER_ALIGNMENT);
        favoritePanel.add(favoriteLabel);

        favoriteButton = new JButton(heartEmptyIcon);
        favoriteButton.setContentAreaFilled(false);
        favoriteButton.setBorderPainted(false);
        favoriteButton.addActionListener(e -> {
            try {
                toggleFavorite();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        JPanel favoriteButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        favoriteButtonPanel.add(favoriteButton);
        favoritePanel.add(favoriteButtonPanel);

        contentPanel.add(favoritePanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Comment Section
        JPanel commentPanel = new JPanel();
        commentPanel.setLayout(new BoxLayout(commentPanel, BoxLayout.Y_AXIS));
        JLabel commentLabel = new JLabel("Your Comment:");
        commentLabel.setFont(new Font("Arial", Font.BOLD, 20));
        commentLabel.setAlignmentX(CENTER_ALIGNMENT);
        commentPanel.add(commentLabel);

        commentInput = new JTextField(20);
        commentInput.setMaximumSize(new Dimension(Integer.MAX_VALUE, commentInput.getPreferredSize().height));
        commentPanel.add(commentInput);

        contentPanel.add(commentPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Buttons Section
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        submitButton = new JButton("Submit");
        backButton = new JButton("Back");
        buttonPanel.add(submitButton);
        buttonPanel.add(backButton);
        contentPanel.add(buttonPanel);

        // Add functionality to buttons
        submitButton.addActionListener(e -> {
            try {
                submit();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        backButton.addActionListener(e -> {
            CardLayout layout = (CardLayout) getParent().getLayout();
            layout.show(getParent(), "ClickView");
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
    }

    private void submit() throws IOException {
        String newComment = commentInput.getText().trim();
        if (!newComment.isEmpty() || selectedRating > 0 || isFavorited) {
            if (!newComment.isEmpty()) {
                artwork.addComment(newComment);
                commentController.execute(artwork,newComment);
            }
            if (selectedRating > 0) {
                artwork.setRating(selectedRating);
                ratingController.execute(artwork.getId(),selectedRating);
            }
            artwork.setFavorited(isFavorited);
            favoriteController.execute(artwork);
            cfrViewModel.firePropertyChanged();
            commentInput.setText("");
            JOptionPane.showMessageDialog(this, "Thank you for your feedback!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Please provide a rating, comment, or favorite.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        artwork = cfrViewModel.getSelectedArtwork();
        //System.out.println(evt.getPropertyName());
        //System.out.println(artwork.getRating());
        if ("CFRView".equals(evt.getPropertyName())) {
            //System.out.println(artwork.getRating());
            updateState(artwork);
            cardLayout.show(mainPanel, viewName);
        }
        revalidate();
        repaint();
    }

    private void updateState(Artwork artwork) {
        if (artwork != null) {
            //System.out.println(artwork.getRating());
            int artRating = artwork.getRating();
            showRating(artRating);

            isFavorited = artwork.checkFavorited();
            favoriteButton.setIcon(isFavorited ? heartFilledIcon : heartEmptyIcon);
        } else {
            showRating(0);
            isFavorited = false;
            favoriteButton.setIcon(heartEmptyIcon);
        }
    }

    private void showRating(int artRating) {
        for (int i = 0; i < stars.length; i++) {
            if (i < artRating) {
                stars[i].setIcon(starFilledIcon); // Set filled icon for rated stars
            } else {
                stars[i].setIcon(starEmptyIcon); // Set empty icon for remaining stars
            }
        }
    }

    public String getViewName() {
        return viewName;
    }
}
