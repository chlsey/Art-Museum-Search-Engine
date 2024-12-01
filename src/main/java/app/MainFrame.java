package app;

import java.awt.*;

import javax.swing.*;

import view.ClickView;
import view.SearchView;

/**
 * Main frame class.
 */
public class MainFrame extends JFrame {
    private static final int WIDTH = 900;
    private static final int HEIGHT = 900;
    private final SearchView searchView;
    private final ClickView clickView;

    public MainFrame(SearchView searchView, ClickView clickView) {
        this.searchView = searchView;
        this.clickView = clickView;

        setLayout(new CardLayout());
        add(searchView, "SearchView");

        setTitle("Artwork Explorer");
        setSize(WIDTH, WIDTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
