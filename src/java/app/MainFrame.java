package app;

import view.ClickView;
import view.SearchView;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private final SearchView searchView;
    private final ClickView clickView;

    public MainFrame(SearchView searchView, ClickView clickView) {
        this.searchView = searchView;
        this.clickView = clickView;

        setLayout(new CardLayout());
        add(searchView, "SearchView");
        //add(clickView, "ClickView");

        setTitle("Artwork Explorer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
