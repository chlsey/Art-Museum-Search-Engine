package view;

import interface_adapters.CFRViewModel;
import interface_adapters.comment.CommentState;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class CFRView extends JPanel implements PropertyChangeListener {
    private final String viewName = "cfr";
    private final CFRViewModel cfrViewModel;
    private final JTextField commentInput;
    private final JButton submitButton;

    public CFRView(CFRViewModel cfrViewModel) {
        this.cfrViewModel = cfrViewModel;
        this.cfrViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        JLabel inputLabel = new JLabel("Add a comment:");
        commentInput = new JTextField(20);
        submitButton = new JButton("Submit");

        inputPanel.add(inputLabel);
        inputPanel.add(commentInput);
        inputPanel.add(submitButton);

        add(inputPanel, BorderLayout.SOUTH);

        submitButton.addActionListener(e -> submitComment());
    }

    private void submitComment() {
        String newComment = commentInput.getText().trim();
        if (!newComment.isEmpty()) {
            CommentState commentState = cfrViewModel.getCommentState();
            if (commentState != null) {
                commentState.addComment(newComment);
                cfrViewModel.firePropertyChanged();
            }
            commentInput.setText("");
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Update the comments display when the model changes
        CommentState updatedState = cfrViewModel.getCommentState();
    }
}

