package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import interface_adapter.notes.NoteState;
import interface_adapter.notes.NotesController;

public class NotesView extends JPanel {
    private String viewName = "Notes";
    private JFrame frame;
    private JTextArea notesArea;
    private NotesController notesController;
    private NoteState noteState;
    private String savedNotes = "";
    private JButton saveButton = new JButton("Save");
    private JButton backButton = new JButton("Back");
    private Image backgroundImage;

    public NotesView() {
        try {
            backgroundImage = ImageIO.read(new File("images/BG5.png")); // Replace with the path to your image
            if (backgroundImage == null) {
                System.out.println("Error: Image not found.");
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle exception if image is not found
        }

        this.noteState = NoteState.getInstance();
        // Create and set up the notes area with scroll
        notesArea = new JTextArea(noteState.getSavedNotes());
        notesArea.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(notesArea);

        // Create a panel for the notes area
        JPanel notesPanel = new JPanel();
        notesPanel.setLayout(new BoxLayout(notesPanel, BoxLayout.Y_AXIS)); // Stack elements vertically
        notesPanel.add(scrollPane); // Add scrollable notes area to panel

        // Add action listeners for the buttons
        saveButton.addActionListener(e -> noteState.setSavedNotes(notesArea.getText()));
        backButton.addActionListener(e -> notesController.switchToMealPlanView());

        // Create a panel for the buttons and center them
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Center the buttons
        buttonPanel.add(saveButton);
        buttonPanel.add(backButton);

        // Set layout for the main NotesView
        setLayout(new BorderLayout());
        add(notesPanel, BorderLayout.CENTER); // Add notes panel in center
        add(buttonPanel, BorderLayout.SOUTH); // Add butto

    }
    public String getViewName() {
        return viewName;
    }

    public void setNotesController(NotesController notesController) {
        this.notesController = notesController;
    }
}

