package view;

import javax.swing.*;
import java.awt.*;

import interface_adapter.notes.NoteState;
import interface_adapter.notes.NotesController;

public class NotesView {
    private JFrame frame;
    private JTextArea notesArea;
    private NotesController notesController;
    private NoteState noteState;
    private String savedNotes = "";
    private JButton saveButton = new JButton("Save");
    private JButton backButton = new JButton("Back");

    public NotesView() {
        frame = new JFrame("Notes");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        notesArea = new JTextArea(noteState.getSavedNotes()); // Get notes saved in memory
        notesArea.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(notesArea);

        // Save and Back buttons
        saveButton.addActionListener(e -> noteState.setSavedNotes(notesArea.getText()));
        backButton.addActionListener(e -> notesController.switchToMealPlanView());

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(backButton);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public void setNotesController(NotesController notesController) {
        this.notesController = notesController;
    }
}

