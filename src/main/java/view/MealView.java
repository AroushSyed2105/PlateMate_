package view;

import data_access.DBUserDataAccessObject;
import entity.CommonUserFactory;
import interface_adapter.profile.ProfileState;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MealView extends JPanel {

    private final String viewName = "MealPlan";

    public MealView() throws IOException {
        // Set layout for the panel
        this.setLayout(new BorderLayout());

        // Set a subtle gradient-like background with green shades
        this.setBackground(new Color(232, 245, 233)); // Light green background

        // Add a scrollable panel to display the meal plan
        JPanel mealPlanPanel = new JPanel();
        mealPlanPanel.setLayout(new BoxLayout(mealPlanPanel, BoxLayout.Y_AXIS));
        mealPlanPanel.setOpaque(false); // Transparent to show parent background

        JScrollPane scrollPane = new JScrollPane(mealPlanPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remove scroll pane border
        scrollPane.getViewport().setOpaque(false); // Transparent viewport
        scrollPane.setOpaque(false); // Transparent scroll pane
        this.add(scrollPane, BorderLayout.CENTER);

        // Initialize DAO and parse meal plan
        CommonUserFactory commonUserFactory = new CommonUserFactory();
        DBUserDataAccessObject dao = new DBUserDataAccessObject(commonUserFactory);
        String TempMeal = dao.generateMealPlan("Halal, almonds");
        // Sample meal plan string
//        // Parse the meal plan
        Map<String, String> unformattedPlan = dao.fullMealPlan(TempMeal);
        System.out.println("TempMeal" + TempMeal);
        System.out.println("NO" + unformattedPlan);

        // Add meals to the panel
        for (Map.Entry<String, String> entry : unformattedPlan.entrySet()) {
            String mealType = entry.getKey();
            String mealInfo = entry.getValue();
            System.out.println(mealType);

            // Create a panel for each meal with light shadow
            JPanel mealCard = new JPanel();
            mealCard.setLayout(new BoxLayout(mealCard, BoxLayout.Y_AXIS));
            mealCard.setBackground(new Color(219, 232, 215)); // Soft light green for card background
            mealCard.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(219, 232, 215), 1, true), // Subtle green border
                    new EmptyBorder(15, 15, 15, 15))); // Inner padding

            // Add meal type label as a header
            JLabel mealTypeLabel = new JLabel(mealType);
            mealTypeLabel.setFont(new Font("Times New Roman", Font.BOLD, 30)); // Modern font
            mealTypeLabel.setForeground(new Color(34, 139, 34)); // Forest green for headers
            mealTypeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            mealCard.add(mealTypeLabel);

            // Add a divider line
            JSeparator divider = new JSeparator();
            divider.setForeground(new Color(143, 188, 143)); // Subtle sage green divider
            divider.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
            mealCard.add(Box.createRigidArea(new Dimension(0, 10))); // Space before divider
            mealCard.add(divider);
            mealCard.add(Box.createRigidArea(new Dimension(0, 10))); // Space after divider

            // Add a text area for the meal information
            JTextArea mealInfoText = new JTextArea(mealInfo);
            mealInfoText.setWrapStyleWord(true);
            mealInfoText.setLineWrap(true);
            mealInfoText.setEditable(false);
            mealInfoText.setBackground(new Color(255, 255, 240)); // Matches card background
            mealInfoText.setFont(new Font("Times New Roman", Font.PLAIN, 16)); // Modern plain font
            mealInfoText.setForeground(new Color(0, 0, 0)); // Darker green for text
            mealCard.add(mealInfoText);

            // Add some space between meals
            mealCard.add(Box.createRigidArea(new Dimension(0, 15)));
            mealPlanPanel.add(mealCard);
            mealPlanPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Space between cards
        }

        // Refresh the panel to display the content
        revalidate();
        repaint();
    }

    public String getViewName() {
        return viewName;
    }

    public String getUserPreferences() {
        ProfileState currentState = new ProfileState();
        String tempshit = String.join(",",
                String.join(",", currentState.getAllergies()),
                String.join(",", currentState.getDietaryRestrictions()),
                String.join(",", currentState.getHealthGoals()));
        System.out.println(tempshit);
        return tempshit;
    }
}
