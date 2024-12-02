package view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.*;

import org.jetbrains.annotations.NotNull;

import entities.Artwork;
import interface_adapters.CfrViewModel;
import interface_adapters.click_art.ClickArtController;
import interface_adapters.click_art.ClickArtViewModel;

/**
 * Click view.
 */
public class ClickView extends JPanel implements PropertyChangeListener {
    private static final int FORTY = 40;
    private static final int FIFTY = 50;
    private static final int THIRTY = 30;
    private static final int SIXTEEN = 16;
    private static final int TEN = 10;
    private static final int TWENTY_EIGHT = 28;
    private static final int HUNDRED = 100;
    private static final int TWO_HUNDRED = 200;
    private static final int FOUR_HUNDRED = 400;
    private static final String ARIAL = "Arial";

    private final String viewName = "ClickView";
    private final ClickArtController clickArtController;
    private final ClickArtViewModel clickArtViewModel;
    private final CfrViewModel cfrViewModel;

    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private final JPanel detailsPanel;

    private final JLabel imageLabel;
    private final JLabel titleLabel;
    private final JLabel artistLabel;
    private final JLabel rating;
    private final JLabel comment;
    private final JLabel favorite;
    private final JTextArea descriptionArea;

    /**
     * Click view.
     * @param clickArtController clickArtController
     * @param clickArtViewModel clickArtViewModel
     * @param cfrViewModel cfrViewModel
     */
    public ClickView(ClickArtController clickArtController, ClickArtViewModel clickArtViewModel,
                     CfrViewModel cfrViewModel) {
        this.clickArtController = clickArtController;
        this.clickArtViewModel = clickArtViewModel;
        this.cfrViewModel = cfrViewModel;
        clickArtViewModel.addPropertyChangeListener(this);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.add(Box.createRigidArea(new Dimension(0, THIRTY)));

        imageLabel = new JLabel();
        imageLabel.setAlignmentX(CENTER_ALIGNMENT);
        imageLabel.setPreferredSize(new Dimension(FOUR_HUNDRED, FOUR_HUNDRED));
        imageLabel.repaint();
        detailsPanel.add(imageLabel);

        titleLabel = new JLabel();
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        titleLabel.setFont(new Font(ARIAL, Font.BOLD, FORTY));
        detailsPanel.add(Box.createRigidArea(new Dimension(0, FIFTY)));
        detailsPanel.add(titleLabel);

        artistLabel = new JLabel();
        artistLabel.setAlignmentX(CENTER_ALIGNMENT);
        artistLabel.setFont(new Font(ARIAL, Font.PLAIN, THIRTY));
        detailsPanel.add(Box.createRigidArea(new Dimension(0, THIRTY)));
        detailsPanel.add(artistLabel);

        descriptionArea = new JTextArea(1 + 1 + 1, TEN);
        descriptionArea.setEditable(false);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setFont(new Font("Serif", Font.PLAIN, SIXTEEN));
        detailsPanel.add(Box.createRigidArea(new Dimension(0, FIFTY)));
        detailsPanel.add(descriptionArea);

        final JPanel cfrPanel = new JPanel();
        cfrPanel.setLayout(new BoxLayout(cfrPanel, BoxLayout.Y_AXIS));
        cfrPanel.setAlignmentX(CENTER_ALIGNMENT);

        rating = new JLabel();
        rating.setFont(new Font(ARIAL, Font.BOLD, TWENTY_EIGHT));
        favorite = new JLabel();
        favorite.setFont(new Font(ARIAL, Font.BOLD, TWENTY_EIGHT));
        comment = new JLabel();
        comment.setFont(new Font(ARIAL, Font.BOLD, TWENTY_EIGHT));
        comment.setHorizontalAlignment(SwingConstants.LEFT);

        final JPanel frPanel = new JPanel();
        frPanel.setLayout(new BoxLayout(frPanel, BoxLayout.X_AXIS));
        frPanel.setAlignmentX(CENTER_ALIGNMENT);
        frPanel.add(rating);
        frPanel.add(Box.createRigidArea(new Dimension(HUNDRED, 0)));
        frPanel.add(favorite);
        cfrPanel.add(frPanel);
        cfrPanel.add(Box.createRigidArea(new Dimension(0, THIRTY)));
        final JPanel commentPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        commentPanel.add(comment);
        cfrPanel.add(commentPanel);

        detailsPanel.add(Box.createRigidArea(new Dimension(0, FIFTY)));
        detailsPanel.add(cfrPanel);

        final JPanel buttons = getButtons(clickArtController);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, FIFTY)));
        detailsPanel.add(buttons);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, HUNDRED)));

        mainPanel.add(detailsPanel, "DetailsView");

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        cardLayout.show(mainPanel, "DetailsView");
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    @NotNull
    private JPanel getButtons(ClickArtController clickArtController) {
        final JPanel buttons = new JPanel();
        final JButton cfrButton = new JButton("Rate It!");
        cfrButton.addActionListener(event -> {
            cfrViewModel.setSelectedArtwork(clickArtViewModel.getSelectedArtwork());
            clickArtController.switchToCFR(clickArtViewModel.getSelectedArtwork());
            cfrViewModel.firePropertyChanged("CFRView");
            final CardLayout cardLayout = (CardLayout) getParent().getLayout();
            cardLayout.show(getParent(), "CFRView");
        });

        final JButton backButton = new JButton("Back");
        backButton.addActionListener(event -> {
            try {
                clickArtController.execute(clickArtViewModel.getSelectedArtwork());
            }
            catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            clickArtController.switchToSearch();
            CardLayout cardLayout = (CardLayout) getParent().getLayout();
            cardLayout.show(getParent(), "search");
        });
        buttons.add(cfrButton);
        buttons.add(backButton);
        return buttons;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (ClickArtViewModel.STATE_CHANGED_PROPERTY.equals(evt.getPropertyName())) {
            final Artwork selectedArtwork = clickArtViewModel.getSelectedArtwork();
            if (selectedArtwork != null) {
                URI newuri = null;
                try {
                    newuri = new URI(selectedArtwork.getImageUrl());
                }
                catch (URISyntaxException event) {
                    throw new RuntimeException(event);
                }
                ImageIcon imageIcon;
                if (newuri.isAbsolute()) {
                    try {
                        imageIcon = new ImageIcon(newuri.toURL());
                    }
                    catch (MalformedURLException event) {
                        throw new RuntimeException(event);
                    }
                }
                else {
                    imageIcon = new ImageIcon(selectedArtwork.getImageUrl());
                }

                final Image image = imageIcon.getImage();
                final Image newimg = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                imageIcon = new ImageIcon(newimg);

                imageLabel.setIcon(imageIcon);
                imageLabel.setPreferredSize(new Dimension(TWO_HUNDRED, TWO_HUNDRED));

                titleLabel.setText("Title: " + selectedArtwork.getTitle());
                artistLabel.setText("Artist: " + selectedArtwork.getArtistName());
                descriptionArea.setText(selectedArtwork.getDescription());
                rating.setText("Rating: " + selectedArtwork.getRating());
                comment.setText("Comment: " + selectedArtwork.getLastComment());

                if (selectedArtwork.checkFavorited()) {
                    favorite.setText("It's my favorite!");
                }
                else {
                    favorite.setText("It's not my favorite");
                }

                cardLayout.show(mainPanel, "DetailsView");
            }
            else {
                cardLayout.show(mainPanel, "GalleryView");
            }
        }
        revalidate();
        repaint();
    }

    public String getViewName() {
        return viewName;
    }
}
