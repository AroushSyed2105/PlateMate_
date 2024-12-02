package view;

import data_access.DBUserDataAccessObject;
import entity.CommonUserFactory;
import interface_adapter.Calorie.CalorieController;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;

public class CalorieView extends JPanel {
    private final String viewName = "CalorieView";
    private final Map<String, Integer> plannedCalories; // Planned calorie data
    private final Map<String, Integer> actualCalories; // Actual calorie data entered by the user
    private final JTextArea summaryTextArea;
    private CalorieController calorieController;

    CommonUserFactory commonuserFactory = new CommonUserFactory();
    DBUserDataAccessObject dao = new DBUserDataAccessObject(commonuserFactory);

    public CalorieView() throws IOException {

        String planPlan = dao.generateMealPlan("Halal, avocado");

        List<Integer> extractedCalories = dao.extractCalories(planPlan);
        Map<String, String> fullPlan = dao.fullMealPlan(planPlan);
        List<String> mealNames = new ArrayList<>(fullPlan.keySet());

        plannedCalories = new LinkedHashMap<>();
        for (int i = 0; i < mealNames.size(); i++) {
            plannedCalories.put(mealNames.get(i), extractedCalories.get(i));
        }

        actualCalories = new LinkedHashMap<>();

        this.setLayout(new BorderLayout());
        Color customBackgroundColor = new Color(219, 232, 215);
        this.setBackground(customBackgroundColor);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(customBackgroundColor);

        JPanel mealPlanPanel = new JPanel();
        mealPlanPanel.setLayout(new BoxLayout(mealPlanPanel, BoxLayout.Y_AXIS));
        mealPlanPanel.setOpaque(false);

        for (String mealName : plannedCalories.keySet()) {
            JPanel mealPanel = createMealPanel(mealName, plannedCalories.get(mealName), customBackgroundColor);
            mealPlanPanel.add(mealPanel);
            mealPlanPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing
        }

        // Add back button
        JButton backButton = new JButton("Back to Profile");
        backButton.addActionListener(e -> calorieController.switchToProfileView());

        // Summary Section
        summaryTextArea = new JTextArea();
        summaryTextArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        summaryTextArea.setAlignmentY(Component.CENTER_ALIGNMENT);
        summaryTextArea.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        summaryTextArea.setEditable(false);
        summaryTextArea.setBackground(customBackgroundColor);
        updateSummary();

        JScrollPane summaryScrollPane = new JScrollPane(summaryTextArea);
        summaryScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        summaryScrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0, 100, 0)),
                "Your Nutritional Summary",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Times New Roman", Font.BOLD, 24),
                new Color(0, 0, 0)
        ));
        summaryScrollPane.setPreferredSize(new Dimension(400, 200));
        summaryScrollPane.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.add(mealPlanPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Add spacing before summary
        mainPanel.add(summaryScrollPane);

        mainPanel.add(backButton);
        // Wrap mainPanel in a JScrollPane
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createMealPanel(String mealName, int plannedCal, Color backgroundColor) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(mealName));
        panel.setBackground(backgroundColor);

        TitledBorder titleBorder = (TitledBorder) panel.getBorder();
        titleBorder.setTitleFont(new Font("Times New Roman", Font.BOLD, 16));
        titleBorder.setTitleColor(Color.BLACK);

        JLabel plannedLabel = new JLabel("Planned: " + plannedCal + " kcal", JLabel.CENTER);
        plannedLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        panel.add(plannedLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBackground(backgroundColor);
        JLabel actualLabel = new JLabel("Enter Actual Amount: ");
        actualLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        inputPanel.add(actualLabel, BorderLayout.NORTH);
        JTextField inputField = new JTextField();
        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener((ActionEvent e) -> {
            String input = inputField.getText().trim();
            try {
                int actualCal = Integer.parseInt(input);
                actualCalories.put(mealName, actualCal);
                updateSummary();
                inputField.setText("");
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
                    "%n%-10sPlanned = %4d kcal, Actual = %4d kcal%n",
                    mealName+":  ", totalPlanned, totalActual
            ));
        }

        summary.append(String.format(
                "%n%-10sPlanned = %4d kcal, Actual = %4d kcal%n",
                "Total:", totalPlanned, totalActual
        ));
        summaryTextArea.setText(summary.toString());
    }

    public String getViewName() {
        return viewName;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Calorie Tracker");
            CalorieView calorieView = null;
            try {
                calorieView = new CalorieView();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            frame.add(calorieView);
            frame.setSize(800, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    public void setCalorieController(CalorieController calorieController) {
        this.calorieController = calorieController;
    }
}
