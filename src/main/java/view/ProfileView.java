package view;
import java.util.Arrays;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.profile.ProfileController;
import interface_adapter.profile.ProfileViewModel;

/**
 * The View for the Profile Use Case.
 */
public class ProfileView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "profile";

    private final ProfileViewModel profileViewModel;
    private final JTextField allergiesInputField = new JTextField(20);
    private final JTextField dietaryRestrictionsInputField = new JTextField(20);
    private final JTextArea healthGoalsInputField = new JTextArea(5, 20);
    private final JTextField usernameInputField = new JTextField(20);
    private ProfileController profileController;

    private final JButton saveButton;
    private final JButton cancelButton;

    public ProfileView(ProfileViewModel profileViewModel) {
        this.profileViewModel = profileViewModel;
        profileViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("Profile Settings");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final LabelTextPanel allergiesInfo = new LabelTextPanel(
                new JLabel("Allergies:"), allergiesInputField);
        final LabelTextPanel dietaryInfo = new LabelTextPanel(
                new JLabel("Dietary Restrictions:"), dietaryRestrictionsInputField);

        healthGoalsInputField.setLineWrap(true);
        healthGoalsInputField.setWrapStyleWord(true);
        JPanel healthGoalsPanel = new JPanel();
        healthGoalsPanel.setLayout(new BoxLayout(healthGoalsPanel, BoxLayout.Y_AXIS));
        healthGoalsPanel.add(new JLabel("Health Goals:"));
        healthGoalsPanel.add(healthGoalsInputField);

        // Buttons
        final JPanel buttons = new JPanel();
        saveButton = new JButton("Save");
        buttons.add(saveButton);
        cancelButton = new JButton("Cancel");
        buttons.add(cancelButton);

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                // Split the input fields into String[] based on commas (or any delimiter you choose)
                String[] allergiesArray = allergiesInputField.getText().split(",");
                String[] dietaryRestrictionsArray = dietaryRestrictionsInputField.getText().split(",");
                String[] healthGoalsArray = healthGoalsInputField.getText().split(",");

                // Call execute with the split input arrays
                profileController.execute(
                        allergiesArray,
                        healthGoalsArray,
                        dietaryRestrictionsArray,
                        usernameInputField.getText()
                );
            }
        });


        cancelButton.addActionListener(this);

        addAllergiesListener();
        addDietaryRestrictionsListener();
        addHealthGoalsListener();

        // Layout
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(allergiesInfo);
        this.add(dietaryInfo);
        this.add(healthGoalsPanel);
        this.add(buttons);
    }

    private void addAllergiesListener() {
        allergiesInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                profileViewModel.getState().setAllergies(allergiesInputField.getText().split(","));
            }
            public void insertUpdate(DocumentEvent e) { documentListenerHelper(); }
            public void removeUpdate(DocumentEvent e) { documentListenerHelper(); }
            public void changedUpdate(DocumentEvent e) { documentListenerHelper(); }
        });
    }

    private void addDietaryRestrictionsListener() {
        dietaryRestrictionsInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                profileViewModel.getState().setDietaryRestrictions(dietaryRestrictionsInputField.getText().split(","));
            }
            public void insertUpdate(DocumentEvent e) { documentListenerHelper(); }
            public void removeUpdate(DocumentEvent e) { documentListenerHelper(); }
            public void changedUpdate(DocumentEvent e) { documentListenerHelper(); }
        });
    }

    private void addHealthGoalsListener() {
        healthGoalsInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                profileViewModel.getState().setHealthGoals(healthGoalsInputField.getText().split(","));
            }
            public void insertUpdate(DocumentEvent e) { documentListenerHelper(); }
            public void removeUpdate(DocumentEvent e) { documentListenerHelper(); }
            public void changedUpdate(DocumentEvent e) { documentListenerHelper(); }
        });
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "Cancel not implemented yet.");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        JOptionPane.showMessageDialog(this, "Profile updated successfully!");
    }

    public void setProfileController(ProfileController controller) {
        this.profileController = controller;
    }

    public String getViewName() {
        return viewName;
    }
}