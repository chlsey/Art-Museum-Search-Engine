package view;

import interface_adapters.CFRViewModel;
import interface_adapters.comment.CommentState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class CFRView extends JPanel implements PropertyChangeListener {
    private final String viewName = "CFRView";
    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private final CFRViewModel cfrViewModel;

    private final JTextField commentInput;
    private final JButton submitButton;
    private final JButton backButton;
    private final JLabel ratingLabel;
    private final JLabel favoriteLabel;
    private final JButton favoriteButton;
    private int selectedRating = 0; // Holds the user's selected rating
    private boolean isFavorited = false; // Tracks the favorite state

    private final JButton[] stars;
    private final ImageIcon starEmptyIcon;
    private final ImageIcon starFilledIcon;
    private final ImageIcon heartEmptyIcon;
    private final ImageIcon heartFilledIcon;

    public CFRView(CFRViewModel cfrViewModel) {
        this.cfrViewModel = cfrViewModel;
        this.cfrViewModel.addPropertyChangeListener(this);

        // Load image icons
        ImageIcon rawStarEmpty = new ImageIcon("src/images/star_empty.png");
        ImageIcon rawStarFilled = new ImageIcon("src/images/star_filled.png");
        ImageIcon rawHeartEmpty = new ImageIcon("src/images/heart_empty.png");
        ImageIcon rawHeartFilled = new ImageIcon("src/images/heart_filled.png");
        Image imageStarEmpty = rawStarEmpty.getImage();
        Image imageStarFilled = rawStarFilled.getImage();
        Image imageHeartEmpty = rawHeartEmpty.getImage();
        Image imageHeartFilled = rawHeartFilled.getImage();
        Image newStarEmpty = imageStarEmpty.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
        Image newStarFilled = imageStarFilled.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
        Image newHeartEmpty = imageHeartEmpty.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
        Image newHeartFilled = imageHeartFilled.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
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

        // Star Rating
        JPanel starPanel = new JPanel();
        ratingLabel = new JLabel("Rate This Work");
        ratingLabel.setFont(new Font("Arial", Font.BOLD, 20));
        starPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        starPanel.add(ratingLabel);
        stars = new JButton[5];
        for (int i = 0; i < 5; i++) {
            int ratingValue = i + 1; // Rating is 1-based
            stars[i] = new JButton(starEmptyIcon);
            stars[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    setRating(ratingValue);
                }
            });

            starPanel.add(stars[i]);
        }
        contentPanel.add(starPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Favorite Button
        JPanel favoritePanel = new JPanel();
        favoriteLabel = new JLabel("Favorite This Work");
        favoriteLabel.setFont(new Font("Arial", Font.BOLD, 20));
        favoritePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        favoritePanel.add(ratingLabel);
        favoriteButton = new JButton(heartEmptyIcon);
        favoriteButton.addActionListener(e -> toggleFavorite());
        favoritePanel.add(favoriteButton);
        contentPanel.add(favoritePanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Comment Input
        JPanel commentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel commentLabel = new JLabel("Your Comment:");
        commentLabel.setFont(new Font("Arial", Font.BOLD, 20));
        commentInput = new JTextField(20);
        commentPanel.add(commentLabel);
        commentPanel.add(commentInput);
        contentPanel.add(commentPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        submitButton = new JButton("Submit");
        backButton = new JButton("Back");
        buttonPanel.add(submitButton);
        buttonPanel.add(backButton);
        contentPanel.add(buttonPanel);

        // Add functionality to buttons
        submitButton.addActionListener(e -> submitComment());
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
            if (i < rating) {
                stars[i].setIcon(starFilledIcon);
            } else {
                stars[i].setIcon(starEmptyIcon);
            }
        }
    }

    /**
     * Toggles the favorite state and updates the heart icon.
     */
    private void toggleFavorite() {
        isFavorited = !isFavorited;
        favoriteButton.setIcon(isFavorited ? heartFilledIcon : heartEmptyIcon);
    }

    /**
     * Handles submitting the comment, rating, and favorite state.
     */
    private void submitComment() {
        String newComment = commentInput.getText().trim();
        if (!newComment.isEmpty() || selectedRating > 0 || isFavorited) {
            CommentState commentState = cfrViewModel.getCommentState();
            if (commentState != null) {
                if (!newComment.isEmpty()) {
                    commentState.addComment(newComment);
                }
                if (selectedRating > 0) {
                    commentState.setRating(selectedRating);
                }
                commentState.setFavorited(isFavorited); // Save favorite state
                cfrViewModel.firePropertyChanged();
            }
            commentInput.setText("");
            JOptionPane.showMessageDialog(this, "Thank you for your feedback!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Please provide a rating, comment, or favorite.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("CRFView".equals(evt.getPropertyName())) {
            cardLayout.show(mainPanel, viewName);
        }
        revalidate();
        repaint();
    }

    public String getViewName() {
        return viewName;
    }
}

