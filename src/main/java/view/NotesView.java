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
        Color customBackgroundColor = new Color(219, 232, 215);
        this.setBackground(customBackgroundColor);
        this.setOpaque(false);

        JPanel notesPanel = new JPanel();
        notesPanel.setLayout(new BoxLayout(notesPanel, BoxLayout.Y_AXIS));
        notesArea = new JTextArea(noteState.getSavedNotes());
        notesArea.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        notesArea.setLineWrap(true);
        notesArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(notesArea);
        scrollPane.setPreferredSize(new Dimension(450, 300));

        notesPanel.add(scrollPane, BorderLayout.CENTER);
        saveButton.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        saveButton.addActionListener(e -> noteState.setSavedNotes(notesArea.getText()));
        saveButton.addActionListener(evt-> JOptionPane.showMessageDialog(this,
                "Notes saved!"));
        backButton.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        backButton.addActionListener(e -> notesController.switchToMealPlanView());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(saveButton);
        buttonPanel.add(backButton);

        setLayout(new BorderLayout());
        add(notesPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

    }
    public String getViewName() {
        return viewName;
    }

    public void setNotesController(NotesController notesController) {
        this.notesController = notesController;
    }
}

