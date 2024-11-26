package view;

import interface_adapters.click_art.*;
import interface_adapters.click_art.ClickArtController;
import interface_adapters.click_art.ClickArtViewModel;
import entities.Artwork;
import use_case.click_art.ClickArtInteractor;
import use_case.click_art.ClickArtOutputBoundary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


public class ClickView extends JPanel implements PropertyChangeListener {
    private final String viewName = "ClickView";
    private final ClickArtController clickArtController;
    private final ClickArtViewModel clickArtViewModel;

    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private final JPanel detailsPanel;
//    private final JPanel galleryPanel;

    private final JLabel imageLabel;
    private final JLabel titleLabel;
    private final JLabel artistLabel;
    private final JLabel rating;
    private final JLabel comment;
    private final JLabel favorite;
    private final JTextArea descriptionArea;



    public ClickView(ClickArtController clickArtController, ClickArtViewModel clickArtViewModel) {
        this.clickArtController = clickArtController;
        this.clickArtViewModel = clickArtViewModel;
        clickArtViewModel.addPropertyChangeListener(this);

        // Initialize the CardLayout and main panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Initialize Gallery panel
//        galleryPanel = new JPanel();
//        galleryPanel.setLayout(new BoxLayout(galleryPanel, BoxLayout.Y_AXIS));
//        JButton viewDetailsButton = new JButton("View Artwork Details");
//        viewDetailsButton.setAlignmentX(CENTER_ALIGNMENT);
//        viewDetailsButton.addActionListener(e -> cardLayout.show(mainPanel, "DetailsView"));
//        galleryPanel.add(viewDetailsButton);
//        galleryPanel.add(new JLabel("Gallery View (Placeholder)"));

        // Initialize Details panel
        detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        //Image label
        imageLabel = new JLabel();
        imageLabel.setAlignmentX(CENTER_ALIGNMENT);
        imageLabel.setPreferredSize(new Dimension(400, 400));
        imageLabel.repaint();
        detailsPanel.add(imageLabel);

        // Title label
        titleLabel = new JLabel();
        titleLabel.setAlignmentX(CENTER_ALIGNMENT); // Center-align the label
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        detailsPanel.add(titleLabel);

        // Artist label
        artistLabel = new JLabel();
        artistLabel.setAlignmentX(CENTER_ALIGNMENT); // Center-align the label
        artistLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        detailsPanel.add(artistLabel);


        // Description text area
        descriptionArea = new JTextArea(3, 10);
        descriptionArea.setEditable(false);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setFont(new Font("Serif", Font.PLAIN, 16));
        //JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);
        //descriptionScrollPane.setAlignmentX(CENTER_ALIGNMENT); // Center-align the scroll pane
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 100)));
        detailsPanel.add(descriptionArea);

        JPanel cfrPanel = new JPanel();
        cfrPanel.setLayout(new BoxLayout(cfrPanel, BoxLayout.Y_AXIS));
        cfrPanel.setAlignmentX(CENTER_ALIGNMENT);

        rating = new JLabel();
        rating.setFont(new Font("Arial", Font.BOLD, 28)); // Larger font
        favorite = new JLabel();
        favorite.setFont(new Font("Arial", Font.BOLD, 28)); // Larger font
        comment = new JLabel();
        comment.setFont(new Font("Arial", Font.BOLD, 28)); // Larger font

        // Add spacing between CFR elements
        JPanel frPanel = new JPanel();
        frPanel.setLayout(new BoxLayout(frPanel, BoxLayout.X_AXIS));
        frPanel.setAlignmentX(CENTER_ALIGNMENT);
        frPanel.add(rating);
        frPanel.add(Box.createRigidArea(new Dimension(100, 0)));
        frPanel.add(favorite);
        cfrPanel.add(frPanel);
        cfrPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        cfrPanel.add(comment);

        // Add the CFR panel to the details panel
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 100)));
        detailsPanel.add(cfrPanel);

        final JPanel buttons = new JPanel();

        //CFR BUTTON
        JButton cfrButton = new JButton("Rate It!");
        cfrButton.addActionListener(e -> {
            clickArtController.switchToCFR();
            CardLayout cardLayout = (CardLayout) getParent().getLayout();
            cardLayout.show(getParent(), "CFRView");
        });

        // Back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            clickArtController.switchToSearch();
            CardLayout cardLayout = (CardLayout) getParent().getLayout();
            cardLayout.show(getParent(), "search");
        });
        buttons.add(cfrButton);
        buttons.add(backButton);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        detailsPanel.add(buttons);

//
//        backButton.addActionListener(e -> {
//            // Set the state to "search" in the view model
//            clickArtViewModel.setState(new ClickArtState("search");
//        });
//        detailsPanel.add(backButton);

        // Add panels to the CardLayout
//        mainPanel.add(galleryPanel, "GalleryView");
        mainPanel.add(detailsPanel, "DetailsView");

        // Add the main panel to the ClickView
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        // Show the gallery view by default
        // cardLayout.show(mainPanel, "GalleryView");
        cardLayout.show(mainPanel, "DetailsView");
        mainPanel.revalidate();
        mainPanel.repaint();
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Check if the state has changed and we are now viewing artwork details
        if (ClickArtViewModel.STATE_CHANGED_PROPERTY.equals(evt.getPropertyName())) {
            Artwork selectedArtwork = clickArtViewModel.getSelectedArtwork();
            if (selectedArtwork != null) {
                URI newuri = null;
                try {
                    newuri = new URI(selectedArtwork.getImageUrl());
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
                ImageIcon imageIcon;
                if (newuri.isAbsolute()) {
                    try {
                        imageIcon = new ImageIcon(newuri.toURL());
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    imageIcon = new ImageIcon(selectedArtwork.getImageUrl());
                }

                Image image = imageIcon.getImage(); // transform it
                Image newimg = image.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
                imageIcon = new ImageIcon(newimg);  // transform it back

                imageLabel.setIcon(imageIcon);
                imageLabel.setPreferredSize(new Dimension(200, 200));

                titleLabel.setText("Title: " + selectedArtwork.getTitle());
                artistLabel.setText("Artist: " + selectedArtwork.getArtistName());
                descriptionArea.setText(selectedArtwork.getDescription());
                rating.setText("Rating: " + selectedArtwork.getRating());
                comment.setText("Comment: " + selectedArtwork.getLastComment());
                if (selectedArtwork.checkFavorited()) {
                    favorite.setText("It's my favorite");
                } else {
                    favorite.setText("It's not my favorite");
                }
                cardLayout.show(mainPanel, "DetailsView"); // Switch to the details view
            } else {
                cardLayout.show(mainPanel, "GalleryView"); // Switch back to the gallery view
            }
        }
        revalidate();
        repaint();
    }

    public String getViewName() {
        return viewName;
    }
}