package view;

import data_access.DBUserDataAccessObject;
import entity.CommonUserFactory;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class MealView extends JPanel {

    private final String viewName = "MealPlan";

    public MealView() {
        // Set layout for the panel
        this.setLayout(new BorderLayout());

        // Set custom background color for the entire MealView panel
        Color customBackgroundColor = new Color(219, 232, 215); // Desired background color
        this.setBackground(customBackgroundColor);

        // Add a scrollable panel to display the meal plan
        JPanel mealPlanPanel = new JPanel();
        mealPlanPanel.setLayout(new BoxLayout(mealPlanPanel, BoxLayout.Y_AXIS));

        // Set custom background color for the mealPlanPanel
        mealPlanPanel.setBackground(customBackgroundColor);

        JScrollPane scrollPane = new JScrollPane(mealPlanPanel);

        // Optional: Set background color for the scroll pane viewport
        scrollPane.getViewport().setBackground(customBackgroundColor);

        this.add(scrollPane, BorderLayout.CENTER);

        CommonUserFactory commonuserFactory = new CommonUserFactory();
        DBUserDataAccessObject dao = new DBUserDataAccessObject(commonuserFactory);

        Map<String, String> rawMealDetails = new LinkedHashMap<>();
        rawMealDetails.put("Breakfast", "- **Recipe:** Halal Scrambled Eggs\n" +
                "- **Instructions:** Whisk eggs, milk (dairy or plant-based), salt, and pepper. Scramble in a pan with halal-certified cooking oil. Serve with toasted pita bread and sliced fresh fruit.\n" +
                "- **Grocery List:** Eggs, milk, salt, pepper, halal-certified cooking oil, pita bread, apples, pears.\n" +
                "- **Ingredients:** Eggs, milk, salt, pepper, pita bread, fresh fruit.\n" +
                "- **Nutrient Breakdown (per serving):\n" +
                "- Calories: 300\n- Carbohydrates: 50g\n- Fat: 10g\n- Protein: 5g\n- Fiber: 7g");

                rawMealDetails.put("Lunch", "- **Recipe:** Grilled Chicken and Vegetable Skewers\n" +
                "- **Instructions:** Marinate chicken cubes in olive oil, garlic, lemon juice, and spices. Skewer with bell peppers, onions, and tomatoes. Grill or bake until cooked. Serve with quinoa and salad.\n" +
                "- **Grocery List:** Chicken, olive oil, garlic, lemon, paprika, cumin, bell peppers, onions, cherry tomatoes, quinoa, mixed greens.\n" +
                "- **Ingredients:** Chicken, olive oil, garlic, lemon, vegetables, quinoa, salad greens.\n" +
                "- **Nutrient Breakdown (per serving):\n" +
                "- Calories: 300\n- Carbohydrates: 5g\n- Fat: 10g\n- Protein: 5g\n- Fiber: 7g");

        rawMealDetails.put("Dinner", "- **Recipe:** Baked Salmon with Lemon and Dill\n" +
                "- **Instructions:** Place salmon on lemon slices and dill. Drizzle olive oil, season with salt and pepper, and bake until flaky. Serve with steamed broccoli and quinoa.\n" +
                "- **Grocery List:** Salmon, lemons, dill, olive oil, salt, pepper, broccoli, quinoa.\n" +
                "- **Ingredients:** Salmon, lemons, dill, olive oil, salt, pepper, broccoli, quinoa.\n" +
                "- **Nutrient Breakdown (per serving):\n" +
                "- Calories: 300\n- Carbohydrates: 50g\n- Fat: 10g\n- Protein: 5g\n- Fiber: 7g");


        Map<String, Map<String, String>> formattedMealPlan = dao.formatMealDetails(rawMealDetails);

        displayFormattedMealPlan(formattedMealPlan, mealPlanPanel); // add the stupid formatted meal plan to the view
    }

    private void displayFormattedMealPlan(Map<String, Map<String, String>> formattedMealPlan, JPanel mealPlanPanel) {
        for (Map.Entry<String, Map<String, String>> mealEntry : formattedMealPlan.entrySet()) {
            String mealName = mealEntry.getKey(); // Breakfast, Lunch, Dinner

            // Add header as bolded and bigger font - breakie, lunchie, and dindin
            JLabel mealHeaderLabel = new JLabel(mealName.toUpperCase(), SwingConstants.CENTER); // titles are centered like a baddie
            mealHeaderLabel.setFont(new Font("Times New Roman", Font.BOLD, 22)); // bold that shittttt
            mealHeaderLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // spacing shit around the header
            mealHeaderLabel.setOpaque(true); // Required to apply background color
            mealHeaderLabel.setBackground(new Color(219, 232, 215)); // Custom background color
            mealPlanPanel.add(mealHeaderLabel); // add that shit :D

            // Subheaders - recipe, instructions.....
            Map<String, String> sections = mealEntry.getValue(); // all the info shit from each meal - breakie....
            for (Map.Entry<String, String> sectionEntry : sections.entrySet()) {
                String sectionName = sectionEntry.getKey(); // recipe, instructions .....
                String sectionContent = sectionEntry.getValue();

                // Making subheaders bold and on left side of page
                JLabel sectionHeaderLabel = new JLabel(sectionName);
                sectionHeaderLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
                sectionHeaderLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // that shit aint moving oopp
                sectionHeaderLabel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 0)); // adding space for readability
                sectionHeaderLabel.setOpaque(true); // Required to apply background color
                sectionHeaderLabel.setBackground(new Color(219, 232, 215)); // Custom background color

                // Add the subheader label to the panel
                mealPlanPanel.add(sectionHeaderLabel); // Add subheader to the panel

                // All the subheaders shit
                JTextArea sectionContentText = new JTextArea(sectionContent);
                sectionContentText.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                sectionContentText.setLineWrap(true); // style shit
                sectionContentText.setWrapStyleWord(true); // style shit
                sectionContentText.setEditable(false); // opp u aint changing
                sectionContentText.setBackground(new Color(219, 232, 215)); // Custom background color for text area
                sectionContentText.setAlignmentX(Component.LEFT_ALIGNMENT); // that shit aint moving oopp
                sectionContentText.setBorder(BorderFactory.createEmptyBorder(0, 40, 10, 10)); // indent that shit cuz i can

                // Add the content text to the panel
                mealPlanPanel.add(sectionContentText);
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MealView Test Screen");
        MealView mealView = new MealView();
        frame.add(mealView);
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public Object getViewName() {
        return viewName;
    }
}
