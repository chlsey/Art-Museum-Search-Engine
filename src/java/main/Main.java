package main;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import static use_case.search.SearchInteractor.searchArtwork;
import entities.*;

public class Main {
    public static String searchText;
    public static void main(String[] args) {
        JLabel label = new JLabel("Hi there!");
        JButton button = new JButton("click me");
        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(button);
        JFrame frame = new JFrame("Museum Search Engine");
        frame.setSize(300, 200);
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        JPanel search = new JPanel();
        search.add(new JLabel("art: "));
        search.add(new JTextField(15));
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(new JButton("go"));
        buttonPanel.add(new JButton("cancel"));
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(search);
        mainPanel.add(buttonPanel);
        mainPanel.add(label);
        frame.setContentPane(mainPanel);
        JTextField searchField = new JTextField(15);
        JButton go = new JButton("go");
        go.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchText = searchField.getText();
                List<Artwork> all = searchArtwork(getSearchText(), "");
                StringBuilder artworks = new StringBuilder();
                for (Artwork art: all) {
                    artworks.append(art.getTitle());
                    label.setText(artworks.toString());
                }
            }
        });
    }

    public static String getSearchText() {
        return searchText;
    }
}
