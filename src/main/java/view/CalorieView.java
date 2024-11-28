package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.LinkedHashMap;
import java.util.Map;

public class CalorieView extends JPanel {
    private final String viewName = "CalorieView";
    private final Map<String, Integer> plannedCalories; // Planned calorie data
    private final Map<String, Integer> actualCalories; // Actual calorie data entered by the user
    private final JTextArea summaryTextArea; // To display progress summary

    public CalorieView() {
        // Initialize planned calories (mock data for now)
        plannedCalories = new LinkedHashMap<>();
        plannedCalories.put("Breakfast", 550);
        plannedCalories.put("Lunch", 350);
        plannedCalories.put("Dinner", 300);

        actualCalories = new LinkedHashMap<>(); // Initialize to track user input

        // Set the main layout and background color
        Color customBackgroundColor = new Color(219, 232, 215); // Desired background color
        this.setLayout(new BorderLayout());
        this.setBackground(customBackgroundColor);
        this.setPreferredSize(new Dimension(600, 500));

        // Create a title with custom background
        JLabel titleLabel = new JLabel("Calorie Tracker", JLabel.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(customBackgroundColor);
        this.add(titleLabel, BorderLayout.NORTH);

        // Create the main panel for meals
        JPanel mealPanel = new JPanel();
        mealPanel.setLayout(new GridLayout(3, 1, 10, 10)); // Adjust rows/cols as needed
        mealPanel.setBackground(customBackgroundColor); // Apply background color
        this.add(mealPanel, BorderLayout.CENTER);

        // Populate the panel with meals
        for (String mealName : plannedCalories.keySet()) {
            mealPanel.add(createMealPanel(mealName, plannedCalories.get(mealName), customBackgroundColor));
        }

        // Add a text area for the summary display
        summaryTextArea = new JTextArea();
        summaryTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        summaryTextArea.setEditable(false);
        summaryTextArea.setBackground(new Color(240, 240, 240));
        JScrollPane summaryScrollPane = new JScrollPane(summaryTextArea);
        summaryScrollPane.setBorder(BorderFactory.createTitledBorder("Calorie Tracking Progress (Single Day)"));
        this.add(summaryScrollPane, BorderLayout.SOUTH);

        // Initialize the summary area
        updateSummary();
    }

    // Helper method to create individual meal panels
    private JPanel createMealPanel(String mealName, int plannedCal, Color backgroundColor) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(mealName)); // Meal name as the title
        panel.setBackground(backgroundColor); // Apply background color

        // Display planned calories
        JLabel plannedLabel = new JLabel("Planned: " + plannedCal + " kcal", JLabel.CENTER);
        plannedLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        panel.add(plannedLabel, BorderLayout.NORTH);

        // Input field and button to enter actual calories
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBackground(backgroundColor); // Apply background color
        JTextField inputField = new JTextField();
        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener((ActionEvent e) -> {
            String input = inputField.getText().trim();
            try {
                int actualCal = Integer.parseInt(input);
                actualCalories.put(mealName, actualCal); // Save actual calorie data
                updateSummary(); // Refresh the summary
                inputField.setText(""); // Clear the input field
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a valid number for actual calories!",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(submitButton, BorderLayout.EAST);
        panel.add(inputPanel, BorderLayout.SOUTH);

        return panel;
    }

    // Method to update the summary display
    private void updateSummary() {
        StringBuilder summary = new StringBuilder();
        int totalPlanned = 0;
        int totalActual = 0;

        for (String mealName : plannedCalories.keySet()) {
            int planned = plannedCalories.get(mealName);
            int actual = actualCalories.getOrDefault(mealName, 0);
            totalPlanned += planned;
            totalActual += actual;

            summary.append(String.format(
                    "%-10s: Planned = %4d kcal, Actual = %4d kcal%n",
                    mealName, planned, actual
            ));
        }

        summary.append(String.format(
                "%n%-10s: Planned = %4d kcal, Actual = %4d kcal%n",
                "Total", totalPlanned, totalActual
        ));

        summaryTextArea.setText(summary.toString());
    }

    public String getViewName() {
        return viewName;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Calorie Tracker");
            CalorieView calorieView = new CalorieView();
            frame.add(calorieView);
            frame.setSize(600, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
