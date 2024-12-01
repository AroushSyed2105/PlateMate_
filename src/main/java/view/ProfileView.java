package view;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
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
    private final JButton toMealPlan;
    private final JButton toCalorie;
    private final JButton toGrocery;
    private final JButton saveButton;
    private final JButton backToMenuButton;
    private Image backgroundImage; // Background image variable


    public ProfileView(ProfileViewModel profileViewModel) {
        try {
            backgroundImage = ImageIO.read(new File("images/BG5.png")); // Replace with the path to your image
            if (backgroundImage == null) {
                System.out.println("Error: Image not found.");
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle exception if image is not found
        }

        // Set layout and make the background transparent
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Ensure the panel is transparent
        this.setOpaque(false);

        // Define font and background color
        Font customFont = new Font("Times New Roman", Font.PLAIN, 16);
        Color customBackgroundColor = new Color(255, 255, 240);

        this.profileViewModel = profileViewModel;
        profileViewModel.addPropertyChangeListener(this);

        // Set layout and make the background transparent
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createVerticalStrut(120));

        this.setBackground(customBackgroundColor);

        final JLabel title = new JLabel(ProfileViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setOpaque(false);
        title.setBackground(customBackgroundColor);

        // Create the JList for allergies with scroll pane
        JList<String> allergiesList = new JList<>(VALID_ALLERGIES);
        allergiesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane allergiesScrollPane = new JScrollPane(allergiesList);
        allergiesScrollPane.setPreferredSize(new Dimension(200, 100));
        allergiesScrollPane.getViewport().setBackground(customBackgroundColor); // Set green background

        // Allergies panel
        JPanel allergiesInfo = new JPanel();
        allergiesInfo.setLayout(new BoxLayout(allergiesInfo, BoxLayout.Y_AXIS));
        allergiesInfo.setBackground(customBackgroundColor);
        JLabel allergiesLabel = new JLabel(profileViewModel.ALLERGIES);
        allergiesLabel.setHorizontalAlignment(SwingConstants.CENTER); // Align to the left
        allergiesLabel.setFont(customFont);
        allergiesInfo.add(allergiesLabel);
        allergiesInfo.add(allergiesScrollPane);


        // Health goals
        LabelTextPanel healthGoals = new LabelTextPanel(
                new JLabel(profileViewModel.HEALTH_GOALS), healthGoalsInputField);
        healthGoals.setBackground(customBackgroundColor);

        // Create the JList for dietary restrictions
        JList<String> dietaryRestrictionsList = new JList<>(VALID_DIETARY_RESTRICTIONS);
        dietaryRestrictionsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane dietaryRestrictionsScrollPane = new JScrollPane(dietaryRestrictionsList);
        dietaryRestrictionsScrollPane.setPreferredSize(new Dimension(200, 100));
        dietaryRestrictionsScrollPane.getViewport().setBackground(customBackgroundColor); // Set green background

        // Dietary restrictions panel
        JPanel dietaryRestrictionsInfo = new JPanel();
        dietaryRestrictionsInfo.setLayout(new BoxLayout(dietaryRestrictionsInfo, BoxLayout.Y_AXIS));
        dietaryRestrictionsInfo.setBackground(customBackgroundColor);
        JLabel dietaryRestrictionsLabel = new JLabel(profileViewModel.DIETARY_RESTRICTIONS);
        dietaryRestrictionsLabel.setFont(customFont);
        dietaryRestrictionsInfo.add(dietaryRestrictionsLabel);
        dietaryRestrictionsInfo.add(dietaryRestrictionsScrollPane);
        dietaryRestrictionsList.getSelectedValuesList();

        // Buttons panel
        final JPanel buttons = new JPanel();
        buttons.setBackground(customBackgroundColor);
        saveButton = new JButton(ProfileViewModel.SAVE_BUTTON_LABEL);
        saveButton.setFont(customFont);
        buttons.add(saveButton);

        toMealPlan = new JButton("To Meal Plan");
        toMealPlan.setFont(customFont);
        buttons.add(toMealPlan);
        backToMenuButton = new JButton(ProfileViewModel.BACK_BUTTON_LABEL);
        buttons.add(backToMenuButton);
        backToMenuButton.setFont(customFont);
        toCalorie = new JButton("Calorie Tracker");
        toCalorie.setFont(customFont);
        buttons.add(toCalorie);
        toGrocery = new JButton("To Grocery List");
        toGrocery.setFont(customFont);
        buttons.add(toGrocery);

        saveButton.addActionListener(evt -> {
            ProfileState currentState = profileViewModel.getState();

            // Fetch selected allergies and dietary restrictions from JList
            List<String> selectedAllergies = allergiesList.getSelectedValuesList();
            List<String> selectedDietaryRestrictions = dietaryRestrictionsList.getSelectedValuesList();

            currentState.setAllergies(selectedAllergies.toArray(new String[0]));
            currentState.setDietaryRestrictions(selectedDietaryRestrictions.toArray(new String[0]));
            currentState.setHealthGoals(healthGoalsInputField.getText().split(","));
            profileViewModel.setState(currentState);

            allergiesList.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    currentState.setAllergies(allergiesList.getSelectedValuesList().toArray(new String[0]));
                    profileViewModel.setState(currentState);
                }
            });

            dietaryRestrictionsList.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    currentState.setDietaryRestrictions(dietaryRestrictionsList.getSelectedValuesList().toArray(new String[0]));
                    profileViewModel.setState(currentState);
                }
            });

            profileController.execute(
                    currentState.getAllergies(),
                    currentState.getHealthGoals(),
                    currentState.getDietaryRestrictions(),
                    currentState.getUsername()
            );

            System.out.println("Saved ProfileState:");
            System.out.println("Allergies: " + String.join(", ", currentState.getAllergies()));
            System.out.println("Dietary Restrictions: " + String.join(", ", currentState.getDietaryRestrictions()));
            System.out.println("Health Goals: " + currentState.getHealthGoals());
            System.out.println("Total: " + currentState.getCombinedDietaryInfo());

            JOptionPane.showMessageDialog(this, "Profile updated successfully!");
        });


        toMealPlan.addActionListener(evt -> {
            // Ensure ProfileState is updated before switching to Meal Plan View
            ProfileState currentState = profileViewModel.getState();
            profileController.execute(
                    currentState.getAllergies(),
                    currentState.getHealthGoals(),
                    currentState.getDietaryRestrictions(),
                    currentState.getUsername()
            );

            // Switch to the Meal Plan View
            profileController.switchToMealPlanView();
        });

        toGrocery.addActionListener(evt -> profileController.switchToGroceryView());
        backToMenuButton.addActionListener(evt -> profileController.switchToLoggedInView());
        toCalorie.addActionListener(evt -> profileController.switchtoCalorieView());



        addAllergiesListener();
        addDietaryRestrictionsListener();
        addHealthGoalsListener();

        // Add components to the main panel
        this.add(title);
        this.add(allergiesInfo);
        this.add(dietaryRestrictionsInfo);
        this.add(healthGoals);
        this.add(buttons);
        //this.add(Box.createVerticalGlue());
        setComponentTransparency();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            // Draw the background image to cover the entire panel
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    // Add this in the constructor, after all components are initialized:
    private void setComponentTransparency() {
        usernameInputField.setOpaque(false);
        // Ensure all panels are transparent
        for (Component c : this.getComponents()) {
            if (c instanceof JPanel) {
                ((JPanel) c).setOpaque(false);
            }
        }
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
    }

    public String getViewName() {
        return viewName;
    }

    public void setProfileController(ProfileController controller) {
        this.profileController = controller;
    }
}



