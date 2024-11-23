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
    private final JPanel galleryPanel;

    private final JLabel titleLabel;
    private final JLabel artistLabel;
    private final JTextArea descriptionArea;


    public ClickView(ClickArtController clickArtController, ClickArtViewModel clickArtViewModel) {
        this.clickArtController = clickArtController;
        this.clickArtViewModel = clickArtViewModel;
        clickArtViewModel.addPropertyChangeListener(this);

        // Initialize the CardLayout and main panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Initialize Gallery panel
        galleryPanel = new JPanel();
        galleryPanel.setLayout(new BoxLayout(galleryPanel, BoxLayout.Y_AXIS));
        JButton viewDetailsButton = new JButton("View Artwork Details");
        viewDetailsButton.setAlignmentX(CENTER_ALIGNMENT);
        viewDetailsButton.addActionListener(e -> cardLayout.show(mainPanel, "DetailsView"));
        galleryPanel.add(viewDetailsButton);
        galleryPanel.add(new JLabel("Gallery View (Placeholder)"));

        // Initialize Details panel
        detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));

        // Title label
        titleLabel = new JLabel();
        titleLabel.setAlignmentX(CENTER_ALIGNMENT); // Center-align the label
        detailsPanel.add(titleLabel);

        // Artist label
        artistLabel = new JLabel();
        artistLabel.setAlignmentX(CENTER_ALIGNMENT); // Center-align the label
        detailsPanel.add(artistLabel);


        // Description text area
        descriptionArea = new JTextArea(10, 30);
        descriptionArea.setEditable(false);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);
        descriptionScrollPane.setAlignmentX(CENTER_ALIGNMENT); // Center-align the scroll pane
        detailsPanel.add(descriptionScrollPane);

        final JPanel buttons = new JPanel();

        //CFR BUTTON
        JButton cfrButton = new JButton("Rate It!");
        // Back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            clickArtController.switchToSearch();
            CardLayout cardLayout = (CardLayout) getParent().getLayout();
            cardLayout.show(getParent(), "search");
        });
        buttons.add(cfrButton);
        buttons.add(backButton);
        detailsPanel.add(buttons);

//
//        backButton.addActionListener(e -> {
//            // Set the state to "search" in the view model
//            clickArtViewModel.setState(new ClickArtState("search");
//        });
//        detailsPanel.add(backButton);

        // Add panels to the CardLayout
        mainPanel.add(galleryPanel, "GalleryView");
        mainPanel.add(detailsPanel, "DetailsView");

        // Add the main panel to the ClickView
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        // Show the gallery view by default
        cardLayout.show(mainPanel, "GalleryView");
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Check if the state has changed and we are now viewing artwork details
        if (ClickArtViewModel.STATE_CHANGED_PROPERTY.equals(evt.getPropertyName())) {
            Artwork selectedArtwork = clickArtViewModel.getSelectedArtwork();
            if (selectedArtwork != null) {
                titleLabel.setText("Title: " + selectedArtwork.getTitle());
                artistLabel.setText("Artist: " + selectedArtwork.getArtistName());
                descriptionArea.setText(selectedArtwork.getDescription());
                cardLayout.show(mainPanel, "DetailsView"); // Switch to the details view
            } else {
                cardLayout.show(mainPanel, "GalleryView"); // Switch back to the gallery view
            }
        }
    }

    public String getViewName() {
        return viewName;
    }
}