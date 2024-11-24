package app;

import data_access.MealMeal;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MealMealTester {

    // Create an instance of MealMeal
    private final MealMeal mealMeal = new MealMeal();

    @Test
    public void testParseSingleDayMealPlan() {
        String mealPlan = "**Day 1:**\n" +
                "- Breakfast: Scrambled eggs with toast\n" +
                "- Lunch: Grilled chicken with rice and vegetables\n" +
                "- Dinner: Spaghetti with marinara sauce and meatballs";

        Map<String, String> result = mealMeal.parseSingleDayMealPlan(mealPlan);

        assertNotNull(result);
        assertEquals(3, result.size());

        // Test individual meal descriptions
        assertEquals("Scrambled eggs toast", result.get("Breakfast"));
        assertEquals("Grilled chicken rice vegetables", result.get("Lunch"));
        assertEquals("Spaghetti marinara sauce meatballs", result.get("Dinner"));
    }

    @Test
    public void testCleanMealDescription() {
        String description = "Scrambled eggs with toast";
        String cleanedDescription = MealMeal.cleanMealDescription(description);

        assertNotNull(cleanedDescription);
        assertEquals("Scrambled eggs toast", cleanedDescription);
    }

    @Test
    public void testCleanMealDescriptionWithMultipleFillerWords() {
        String description = "Pasta with cheese and tomato sauce on top";
        String cleanedDescription = MealMeal.cleanMealDescription(description);

        assertEquals("Pasta cheese tomato sauce", cleanedDescription);
    }

    @Test
    public void testParseSingleDayMealPlanNoMeals() {
        String mealPlan = "**Day 1:**\n" +
                "- Breakfast: \n" +
                "- Lunch: \n" +
                "- Dinner: ";

        Map<String, String> result = mealMeal.parseSingleDayMealPlan(mealPlan);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testParseSingleDayMealPlanNoDay() {
        String mealPlan = "No day information provided.";

        Map<String, String> result = mealMeal.parseSingleDayMealPlan(mealPlan);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
