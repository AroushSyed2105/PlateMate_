package data_access;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class NutrientTest {

    private MealMeal mealMeal;

    @BeforeEach
    void setUp() {
        // Sample meal plan with 3 meals (Breakfast, Lunch, Dinner)
        String sampleMealPlan = "- Breakfast: Oatmeal with fruits\n" +
                "- Lunch: Grilled Chicken Salad\n" +
                "- Dinner: Creamy Avocado Pasta (Vegan)";
        mealMeal = new MealMeal(sampleMealPlan);
    }

    @Test
    void testExtractNutrients() {
        // Mock the response of meal descriptions, with detailed recipe format for each meal
        List<String> mealResponses = List.of(
                "Recipe: Oatmeal with Fruits\n\n" +
                        "Ingredients:\n" +
                        "- Oats\n- Banana\n- Almonds\n- Honey\n- Chia Seeds\n\n" +
                        "Nutrient Breakdown (per serving):\n" +
                        "- Calories: 300\n- Carbohydrates: 50g\n- Fat: 10g\n- Protein: 5g\n- Fiber: 7g",

                "Recipe: Grilled Chicken Salad\n\n" +
                        "Ingredients:\n" +
                        "- Chicken Breast\n- Mixed Greens\n- Tomatoes\n- Cucumber\n- Olive Oil\n- Lemon Juice\n\n" +
                        "Nutrient Breakdown (per serving):\n" +
                        "- Calories: 350\n- Carbohydrates: 10g\n- Fat: 15g\n- Protein: 40g\n- Fiber: 4g",

                "Recipe: Creamy Avocado Pasta (Vegan)\n\n" +
                        "Ingredients:\n" +
                        "- 12 oz pasta\n- 2 ripe avocados\n- Fresh basil\n- Garlic\n- Olive oil\n- Nutritional yeast\n\n" +
                        "Nutrient Breakdown (per serving):\n" +
                        "- Calories: 550\n- Carbohydrates: 70g\n- Fat: 28g\n- Protein: 12g\n- Fiber: 6g"
        );

        // Call the method
        List<List<String>> nutrients = mealMeal.extractNutrients(mealResponses);
        System.out.println(nutrients);

        // Assertions
        assertEquals(3, nutrients.size());
        assertTrue(nutrients.get(0).contains("Calories: 300"));
        assertTrue(nutrients.get(1).contains("Protein: 40 g"));
        assertTrue(nutrients.get(2).contains("Fat: 28 g"));
    }

    @Test
    void testExtractCalories() {
        // Mock the response of meal descriptions, with detailed recipe format for each meal
        List<String> mealResponses = List.of(
                "Recipe: Oatmeal with Fruits\n\n" +
                        "Ingredients:\n" +
                        "- Oats\n- Banana\n- Almonds\n- Honey\n- Chia Seeds\n\n" +
                        "Nutrient Breakdown (per serving):\n" +
                        "- Calories: 300\n- Carbohydrates: 50g\n- Fat: 10g\n- Protein: 5g\n- Fiber: 7g",

                "Recipe: Grilled Chicken Salad\n\n" +
                        "Ingredients:\n" +
                        "- Chicken Breast\n- Mixed Greens\n- Tomatoes\n- Cucumber\n- Olive Oil\n- Lemon Juice\n\n" +
                        "Nutrient Breakdown (per serving):\n" +
                        "- Calories: 350\n- Carbohydrates: 10g\n- Fat: 15g\n- Protein: 40g\n- Fiber: 4g",

                "Recipe: Creamy Avocado Pasta (Vegan)\n\n" +
                        "Ingredients:\n" +
                        "- 12 oz pasta\n- 2 ripe avocados\n- Fresh basil\n- Garlic\n- Olive oil\n- Nutritional yeast\n\n" +
                        "Nutrient Breakdown (per serving):\n" +
                        "- Calories: 550\n- Carbohydrates: 70g\n- Fat: 28g\n- Protein: 12g\n- Fiber: 6g"
        );

        // Call the method
        List<Integer> calories = mealMeal.extractCalories(mealResponses);

        // Assertions
        assertEquals(List.of(300, 350, 550), calories);
    }

    @Test
    void testRecordActualCalories() {
        // Mock planned calories
        List<Integer> plannedCalories = List.of(550, 350, 300);

        // Simulate user input (for example, actual calories inputted by the user)
        System.setIn(new java.io.ByteArrayInputStream("600\n400\n320\n".getBytes()));

        // Call the method
        List<Integer> actualCalories = mealMeal.recordActualCalories(plannedCalories);

        // Assertions
        assertEquals(List.of(600, 400, 320), actualCalories);
    }

    @Test
    void testDisplayCalorieProgress() {
        // Mock planned and actual calories
        List<Integer> plannedCalories = List.of(550, 350, 300);
        List<Integer> actualCalories = List.of(600, 400, 320);

        // Redirect console output to capture printed output
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        // Call the method
        mealMeal.displayCalorieProgress(plannedCalories, actualCalories);

        // Expected output
        String expectedOutput = """
                Calorie Tracking Progress (Single Day):
                Breakfast: Planned = 550 kcal, Actual = 600 kcal
                Lunch: Planned = 350 kcal, Actual = 400 kcal
                Dinner: Planned = 300 kcal, Actual = 320 kcal
                Total: Planned = 1200 kcal, Actual = 1320 kcal
                """;

        // Validate output
        String normalizedExpected = expectedOutput.replace("\r\n", "\n").trim();
        String normalizedActual = outContent.toString().replace("\r\n", "\n").trim();

        // Validate output
        assertEquals(normalizedExpected, normalizedActual);
    }
}
