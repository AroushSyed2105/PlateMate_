package view;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.profile.ProfileController;
import interface_adapter.profile.ProfileState;
import interface_adapter.profile.ProfileViewModel;
import org.jetbrains.annotations.NotNull;
import use_case.user_profile.ProfileInteractor;

/**
 * The View for the Profile Use Case.
 */
public class ProfileView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "Profile";
    private static final String[] VALID_ALLERGIES = List.of("None", "Almonds", "Brazil nuts", "Cashews", "Hazelnuts", "Macadamia nuts",
            "Pecans", "Pine nuts", "Pistachios", "Walnuts", "Peanuts", "Shrimp", "Lobster", "Crab", "Prawns", "Crayfish",
            "Clams", "Oysters", "Scallops", "Mussels", "Squid", "Octopus", "Salmon", "Tuna", "Cod", "Haddock",
            "Mackerel", "Halibut", "Milk (Cow, Goat, Sheep)", "Cheese", "Yogurt", "Butter", "Cream", "Ghee", "Egg whites",
            "Egg yolks", "Wheat", "Rye", "Barley", "Oats", "Corn (Maize)", "Soybeans", "Soy milk", "Tofu", "Tempeh",
            "Soy protein isolates", "Apples", "Bananas", "Kiwi", "Peaches", "Cherries", "Avocado", "Strawberries",
            "Oranges", "Celery", "Carrots", "Potatoes", "Bell peppers", "Tomatoes", "Lentils", "Chickpeas", "Green peas",
            "Beans (Kidney beans, Black beans)", "Sesame seeds", "Sunflower seeds", "Mustard seeds", "Poppy seeds",
            "Flaxseeds (Linseeds)", "Beef", "Pork", "Chicken", "Turkey", "Lamb", "Buckwheat", "Seaweed", "Mango",
            "Lotus seeds", "Durian", "Lupin", "Mustard", "Cassava", "Plantains", "Baobab fruit", "Chickpeas",
            "Pistachios", "Dates", "Quinoa", "Amaranth", "Corn", "Chia seeds", "Red meat", "Gelatin", "Cinnamon",
            "Nutmeg", "Curry powder", "Paprika", "Mushrooms", "Processed soy products (Soy lecithin)",
            "Hydrolyzed wheat proteins", "Milk proteins in baked goods").toArray(new String[0]);
    private static final String[] VALID_DIETARY_RESTRICTIONS = List.of("Vegan", "Vegetarian", "Pescatarian", "Halal",
            "Kosher", "Gluten-Free", "Dairy-Free").toArray(new String[0]);
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
        profileViewModel.addPropertyChangeListener(this);
        final JLabel title = new JLabel(ProfileViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create the JList to allow multiple allergy selections
        JList<String> allergiesList = new JList<>(VALID_ALLERGIES);
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

        // Create the JList to allow multiple allergy selections
        JList<String> dietaryRestrictionsList = new JList<>(VALID_DIETARY_RESTRICTIONS);
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
        dietaryRestrictionsList.getSelectedValuesList();

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
                                             if (evt.getSource().equals(saveButton)) {
                                                 final ProfileState currentState = getProfileState();
                                                 profileController.execute(
                                                         currentState.getAllergies(),
                                                         currentState.getHealthGoals(),
                                                         currentState.getDietaryRestrictions(),
                                                         currentState.getUsername()
                                                 );
                                                 JOptionPane.showMessageDialog(null, "Save button clicked!");
                                             }
                                         }

            @NotNull
            private ProfileState getProfileState() {
                String[] selectedAllergies = allergiesList.getSelectedValuesList().toArray(new String[0]);
                String[] selectedDietaryRestrictions = dietaryRestrictionsList.getSelectedValuesList().toArray(new String[0]);
                final ProfileState currentState = profileViewModel.getState();

                currentState.setAllergies(selectedAllergies);
                currentState.setDietaryRestrictions(selectedDietaryRestrictions);
                return currentState;
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

