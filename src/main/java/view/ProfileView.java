package view;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.profile.ProfileController;
import interface_adapter.profile.ProfileState;
import interface_adapter.profile.ProfileViewModel;
import use_case.user_profile.ProfileInteractor;

/**
 * The View for the Profile Use Case.
 */
public class ProfileView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "Profile";

    private final ProfileViewModel profileViewModel;
    private final JTextField allergiesInputField = new JTextField(20);
    private final JTextField dietaryRestrictionsInputField = new JTextField(20);
    private final JTextField healthGoalsInputField = new JTextField(20);
    private final JTextField usernameInputField = new JTextField(20);
    private ProfileController profileController;
    private ProfileInteractor profileInteractor;

    private final JButton profile;
    private final JButton toMealPlan;
    private final JButton saveButton;
    private final JButton cancelButton;

    public ProfileView(ProfileViewModel profileViewModel) {
        this.profileViewModel = profileViewModel;
        this.profileInteractor = profileInteractor;
        profileViewModel.addPropertyChangeListener(this);
        final JLabel title = new JLabel(ProfileViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Allergy options
        String[] allergyOptions = {"Peanuts", "Shellfish", "Milk", "Eggs", "Wheat", "Soy", "Tree Nuts"};

        // Create the JList to allow multiple allergy selections
        JList<String> allergiesList = new JList<>(allergyOptions);
        allergiesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        // Add JList to JScrollPane to enable scrolling
        JScrollPane allergiesScrollPane = new JScrollPane(allergiesList);
        allergiesScrollPane.setPreferredSize(new Dimension(200, 100));

        // Create a panel for allergies selection with label
        JPanel allergiesInfo = new JPanel();
        allergiesInfo.setLayout(new BoxLayout(allergiesInfo, BoxLayout.Y_AXIS));
        JLabel allergiesLabel = new JLabel(profileViewModel.ALLERGIES); // Label for allergies
        allergiesInfo.add(allergiesLabel);
        allergiesInfo.add(allergiesScrollPane); // Add the JList inside the scroll pane

        final LabelTextPanel healthGoals = new LabelTextPanel(
                new JLabel(profileViewModel.HEALTH_GOALS), healthGoalsInputField);

        // Allergy options
        String[] dietaryRestrictionOptions = {"Vegan", "Vegetarian", "Pescatarian", "Halal",
                "Kosher", "Gluten-Free", "Dairy-Free"};

        // Create the JList to allow multiple allergy selections
        JList<String> dietaryRestrictionsList = new JList<>(dietaryRestrictionOptions);
        dietaryRestrictionsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        // Add JList to JScrollPane to enable scrolling
        JScrollPane dietaryRestrictionsScrollPane = new JScrollPane(dietaryRestrictionsList);
        dietaryRestrictionsScrollPane.setPreferredSize(new Dimension(200, 100));

        // Create a panel for allergies selection with label
        JPanel dietaryRestrictionsInfo = new JPanel();
        dietaryRestrictionsInfo.setLayout(new BoxLayout(dietaryRestrictionsInfo, BoxLayout.Y_AXIS));
        JLabel dietaryRestrictionsLabel = new JLabel(profileViewModel.DIETARY_RESTRICTIONS); // Label for allergies
        dietaryRestrictionsInfo.add(dietaryRestrictionsLabel);
        dietaryRestrictionsInfo.add(dietaryRestrictionsScrollPane); // Add the JList inside the scroll pane

        // Buttons
        final JPanel buttons = new JPanel();
        saveButton = new JButton(ProfileViewModel.SAVE_BUTTON_LABEL);
        buttons.add(saveButton);
        profile = new JButton("Profile");
        buttons.add(profile);
        toMealPlan = new JButton("To Meal Plan");
        buttons.add(toMealPlan);
        cancelButton = new JButton(ProfileViewModel.CANCEL_BUTTON_LABEL);
        buttons.add(cancelButton);

        saveButton.addActionListener(new ActionListener() {
                                         public void actionPerformed(ActionEvent evt) {
                                             if (evt.getSource().equals(profile)) {
                                                 final ProfileState currentState = profileViewModel.getState();

                                                 profileController.execute(
                                                         currentState.getAllergies(),
                                                         currentState.getHealthGoals(),
                                                         currentState.getDietaryRestrictions(),
                                                         currentState.getUsername()
                                                 );

                                             }
                                         }
                                     }
        );

        toMealPlan.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        profileController.switchToMealPlanView();
                    }
                }
        );


        cancelButton.addActionListener(this);

        addAllergiesListener();
        addDietaryRestrictionsListener();
        addHealthGoalsListener();

        // Layout
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(allergiesInfo);
        this.add(dietaryRestrictionsInfo);
        this.add(healthGoals);
        this.add(buttons);
    }

    private void addAllergiesListener() {
        allergiesInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final ProfileState currentState = profileViewModel.getState();
                currentState.setAllergies(allergiesInputField.getText().split(","));
                profileViewModel.setState(currentState);
            }
            @Override
            public void insertUpdate(DocumentEvent e) { documentListenerHelper(); }

            @Override
            public void removeUpdate(DocumentEvent e) { documentListenerHelper(); }

            @Override
            public void changedUpdate(DocumentEvent e) { documentListenerHelper(); }
        });
    }

    private void addDietaryRestrictionsListener() {
        dietaryRestrictionsInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final ProfileState currentState = profileViewModel.getState();
                currentState.setAllergies(dietaryRestrictionsInputField.getText().split(","));
                profileViewModel.setState(currentState);
            }
            @Override
            public void insertUpdate(DocumentEvent e) { documentListenerHelper(); }

            @Override
            public void removeUpdate(DocumentEvent e) { documentListenerHelper(); }

            @Override
            public void changedUpdate(DocumentEvent e) { documentListenerHelper(); }
        });
    }

    private void addHealthGoalsListener() {
        healthGoalsInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final ProfileState currentState = profileViewModel.getState();
                currentState.setAllergies(healthGoalsInputField.getText().split(","));
                profileViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) { documentListenerHelper(); }

            @Override
            public void removeUpdate(DocumentEvent e) { documentListenerHelper(); }

            @Override
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

    public String getViewName() {
        return viewName;
    }

    public void setProfileController(ProfileController controller) {
        this.profileController = controller;
    }

}

