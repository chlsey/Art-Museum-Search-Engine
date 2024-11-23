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

    private final JLabel titleLabel;
    private final JLabel artistLabel;
    private final JTextArea descriptionArea;

    public ClickView(ClickArtController clickArtController, ClickArtViewModel clickArtViewModel) {
        this.clickArtViewModel = clickArtViewModel;
        this.clickArtController = clickArtController;
        clickArtViewModel.addPropertyChangeListener(this);


        // Set BoxLayout to stack components vertically
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Title label
        titleLabel = new JLabel();
        titleLabel.setAlignmentX(CENTER_ALIGNMENT); // Center-align the label
        add(titleLabel);

        // Artist label
        artistLabel = new JLabel();
        artistLabel.setAlignmentX(CENTER_ALIGNMENT); // Center-align the label
        add(artistLabel);

        // Description text area
        descriptionArea = new JTextArea(10, 30);
        descriptionArea.setEditable(false);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);
        descriptionScrollPane.setAlignmentX(CENTER_ALIGNMENT); // Center-align the scroll pane
        add(descriptionScrollPane);
    }


//    private void displayArtworkDetails(Artwork artwork) {
//        titleLabel.setText("Title: " + artwork.getTitle());
//        artistLabel.setText("Artist: " + artwork.getArtistName());
//        descriptionArea.setText(artwork.getDescription());
//    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Check if the state has changed and we are now viewing artwork details
        System.out.println(evt.getPropertyName());
        if (ClickArtViewModel.STATE_CHANGED_PROPERTY.equals(evt.getPropertyName())) {
            Artwork selectedArtwork = clickArtViewModel.getSelectedArtwork();
            if (clickArtViewModel.getSelectedArtwork() != null) {
                titleLabel.setText("Title: " + selectedArtwork.getTitle());
                artistLabel.setText("Artist: " + selectedArtwork.getArtistName());
                descriptionArea.setText(selectedArtwork.getDescription());
                setVisible(true);
            } else {
                // Hide the view if no artwork is selected
                setVisible(false);
            }
        }
    }

    public String getViewName() {
        return viewName;
    }
}

