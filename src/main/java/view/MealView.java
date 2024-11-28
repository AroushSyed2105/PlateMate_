

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

        // TEMP USER PREFERENCE VARIABLES
        String tempAllergies = "halal";
        String TempFoodRestrictions = "dairy,nuts";

        // UNCOMMENT AFTER
        //String tempRestrctions = tempAllergies + TempFoodRestrictions;

        //String mealmeal = dao.generateMealPlan(TempFoodRestrictions);
        //Map<String,String> unformattedPlan = dao.fullMealPlan(mealmeal);

        String TempMeal = "Welcome to PlateMate!\n" +
                "Generated Meal Plan:\n" +
                "Certainly! Here is a precise meal plan for one day that is halal, nut-free, and dairy-free:\n" +
                "\n" +
                "**Breakfast: Avocado Toast**\n" +
                "\n" +
                "**Ingredients:**\n" +
                "- 2 slices of whole grain bread\n" +
                "- 1 ripe avocado\n" +
                "- 1 small tomato, sliced\n" +
                "- Salt and pepper to taste\n" +
                "- Olive oil\n" +
                "- Fresh parsley or cilantro for garnish\n" +
                "\n" +
                "**Instructions:**\n" +
                "1. Toast the whole grain bread slices until golden brown.\n" +
                "2. Mash the ripe avocado in a bowl and season with salt and pepper.\n" +
                "3. Spread the mashed avocado evenly on the toasted bread slices.\n" +
                "4. Top with sliced tomatoes and a drizzle of olive oil.\n" +
                "5. Garnish with fresh parsley or cilantro.\n" +
                "6. Serve and enjoy!\n" +
                "\n" +
                "**Nutritional Facts:**\n" +
                "- Calories: 350\n" +
                "- Protein: 8g\n" +
                "- Carbohydrates: 30g\n" +
                "- Fat: 22g\n" +
                "- Fiber: 10g\n" +
                "\n" +
                "**Lunch: Grilled Chicken Quinoa Salad**\n" +
                "\n" +
                "**Ingredients:**\n" +
                "- 1 cup cooked quinoa\n" +
                "- 1 grilled chicken breast, sliced\n" +
                "- 1 cup mixed greens\n" +
                "- 1/2 cucumber, sliced\n" +
                "- 1/2 red bell pepper, sliced\n" +
                "- 1/4 red onion, thinly sliced\n" +
                "- Juice of 1 lemon\n" +
                "- 2 tbsp olive oil\n" +
                "- Salt and pepper to taste\n" +
                "\n" +
                "**Instructions:**\n" +
                "1. In a large bowl, combine cooked quinoa, mixed greens, cucumber, red bell pepper, and red onion.\n" +
                "2. Add the grilled chicken slices on top of the salad.\n" +
                "3. In a small bowl, whisk together lemon juice, olive oil, salt, and pepper to make the dressing.\n" +
                "4. Drizzle the dressing over the salad and toss to combine.\n" +
                "5. Serve and enjoy!\n" +
                "\n" +
                "**Nutritional Facts:**\n" +
                "- Calories: 450\n" +
                "- Protein: 30g\n" +
                "- Carbohydrates: 35g\n" +
                "- Fat: 20g\n" +
                "- Fiber: 6g\n" +
                "\n" +
                "**Dinner: Lentil and Vegetable Curry**\n" +
                "\n" +
                "**Ingredients:**\n" +
                "- 1 cup dried lentils, rinsed\n" +
                "- 1 can of diced tomatoes\n" +
                "- 1 onion, chopped\n" +
                "- 2 cloves of garlic, minced\n" +
                "- 1 tsp ground cumin\n" +
                "- 1 tsp ground coriander\n" +
                "- 1 tsp turmeric\n" +
                "- 1 tsp paprika\n" +
                "- 2 cups vegetable broth\n" +
                "- 1 cup mixed vegetables (carrots, bell peppers, zucchini)\n" +
                "- Salt and pepper to taste\n" +
                "- Fresh cilantro for garnish\n" +
                "\n" +
                "**Instructions:**\n" +
                "1. In a large pot, sauté the chopped onion and garlic until softened.\n" +
                "2. Add the ground cumin, coriander, turmeric, and paprika. Stir for 1-2 minutes until fragrant.\n" +
                "3. Add the rinsed lentils, diced tomatoes, vegetable broth, and mixed vegetables to the pot.\n" +
                "4. Bring the mixture to a boil, then reduce heat and simmer for 25-30 minutes until the lentils are tender.\n" +
                "5. Season with salt and pepper to taste.\n" +
                "6. Serve the lentil and vegetable curry garnished with fresh cilantro.\n" +
                "\n" +
                "**Nutritional Facts:**\n" +
                "- Calories: 380\n" +
                "- Protein: 20g\n" +
                "- Carbohydrates: 60g\n" +
                "- Fat: 5g\n" +
                "- Fiber: 15g\n" +
                "\n" +
                "**Grocery List:**\n" +
                "- Whole grain bread\n" +
                "- Avocado\n" +
                "- Tomato\n" +
                "- Mixed greens\n" +
                "- Cucumber\n" +
                "- Red bell pepper\n" +
                "- Red onion\n" +
                "- Chicken breast\n" +
                "- Quinoa\n" +
                "- Lemon\n" +
                "- Olive oil\n" +
                "- Dried lentils\n" +
                "- Can of diced tomatoes\n" +
                "- Onion\n" +
                "- Garlic\n" +
                "- Ground cumin\n" +
                "- Ground coriander\n" +
                "- Turmeric\n" +
                "- Paprika\n" +
                "- Vegetable broth\n" +
                "- Mixed vegetables\n" +
                "- Fresh parsley or cilantro\n" +
                "\n" +
                "Enjoy your delicious and nutritious day of meals!\n" +
                "\n" +
                "Process finished with exit code 0\n" +
                "\n";

        Map<String,String> unformattedPlan = dao.fullMealPlan(TempMeal);

        Map<String, Map<String, String>> formattedMealPlan = dao.formatMealDetails(unformattedPlan);

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
