package view;

import data_access.DBUserDataAccessObject;
import entity.CommonUserFactory;

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
    private final Map<String, Integer> plannedCalories;
    private final Map<String, Integer> actualCalories;
    private final JTextArea summaryTextArea;
    CommonUserFactory commonuserFactory = new CommonUserFactory();
    DBUserDataAccessObject dao = new DBUserDataAccessObject(commonuserFactory);

    public CalorieView() throws IOException {
//        String planPlan = """
//                Welcome to PlateMate!
//                Generated Meal Plan:
//                Certainly! Here is a precise meal plan for one day that is halal, nut-free, and dairy-free:
//
//                **Breakfast: Avocado Toast**
//
//                **Ingredients:**
//                - 2 slices of whole grain bread
//                - 1 ripe avocado
//                - 1 small tomato, sliced
//                - Salt and pepper to taste
//                - Olive oil
//                - Fresh parsley or cilantro for garnish
//
//                **Instructions:**
//                1. Toast the whole grain bread slices until golden brown.
//                2. Mash the ripe avocado in a bowl and season with salt and pepper.
//                3. Spread the mashed avocado evenly on the toasted bread slices.
//                4. Top with sliced tomatoes and a drizzle of olive oil.
//                5. Garnish with fresh parsley or cilantro.
//                6. Serve and enjoy!
//
//                **Nutritional Facts:**
//                - Calories: 350
//                - Protein: 8g
//                - Carbohydrates: 30g
//                - Fat: 22g
//                - Fiber: 10g
//
//                **Lunch: Grilled Chicken Quinoa Salad**
//
//                **Ingredients:**
//                - 1 cup cooked quinoa
//                - 1 grilled chicken breast, sliced
//                - 1 cup mixed greens
//                - 1/2 cucumber, sliced
//                - 1/2 red bell pepper, sliced
//                - 1/4 red onion, thinly sliced
//                - Juice of 1 lemon
//                - 2 tbsp olive oil
//                - Salt and pepper to taste
//
//                **Instructions:**
//                1. In a large bowl, combine cooked quinoa, mixed greens, cucumber, red bell pepper, and red onion.
//                2. Add the grilled chicken slices on top of the salad.
//                3. In a small bowl, whisk together lemon juice, olive oil, salt, and pepper to make the dressing.
//                4. Drizzle the dressing over the salad and toss to combine.
//                5. Serve and enjoy!
//
//                **Nutritional Facts:**
//                - Calories: 450
//                - Protein: 30g
//                - Carbohydrates: 35g
//                - Fat: 20g
//                - Fiber: 6g
//
//                **Dinner: Lentil and Vegetable Curry**
//
//                **Ingredients:**
//                - 1 cup dried lentils, rinsed
//                - 1 can of diced tomatoes
//                - 1 onion, chopped
//                - 2 cloves of garlic, minced
//                - 1 tsp ground cumin
//                - 1 tsp ground coriander
//                - 1 tsp turmeric
//                - 1 tsp paprika
//                - 2 cups vegetable broth
//                - 1 cup mixed vegetables (carrots, bell peppers, zucchini)
//                - Salt and pepper to taste
//                - Fresh cilantro for garnish
//
//                **Instructions:**
//                1. In a large pot, sauté the chopped onion and garlic until softened.
//                2. Add the ground cumin, coriander, turmeric, and paprika. Stir for 1-2 minutes until fragrant.
//                3. Add the rinsed lentils, diced tomatoes, vegetable broth, and mixed vegetables to the pot.
//                4. Bring the mixture to a boil, then reduce heat and simmer for 25-30 minutes until the lentils are tender.
//                5. Season with salt and pepper to taste.
//                6. Serve the lentil and vegetable curry garnished with fresh cilantro.
//
//                **Nutritional Facts:**
//                - Calories: 4000
//                - Protein: 20g
//                - Carbohydrates: 60g
//                - Fat: 5g
//                - Fiber: 15g
//
//                **Grocery List:**
//                - Whole grain bread
//                - Avocado
//                - Tomato
//                - Mixed greens
//                - Cucumber
//                - Red bell pepper
//                - Red onion
//                - Chicken breast
//                - Quinoa
//                - Lemon
//                - Olive oil
//                - Dried lentils
//                - Can of diced tomatoes
//                - Onion
//                - Garlic
//                - Ground cumin
//                - Ground coriander
//                - Turmeric
//                - Paprika
//                - Vegetable broth
//                - Mixed vegetables
//                - Fresh parsley or cilantro
//
//                Enjoy your delicious and nutritious day of meals!
//                """;

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
}
