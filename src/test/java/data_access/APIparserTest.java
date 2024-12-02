package data_access;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;
import data_access.DBUserDataAccessObject;
import entity.CommonUserFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class APIparserTest {

    @Test
    public void testFullMealPlan() {
        String input = "Welcome to PlateMate!\n" +
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
                "- Fiber: 15g\n";

        // Expected output
        Map<String, String> expectedOutput = new LinkedHashMap<>();
        expectedOutput.put("Breakfast", "Avocado Toast\n\n**Ingredients:**\n- 2 slices of whole grain bread\n- 1 ripe avocado\n- 1 small tomato, sliced\n- Salt and pepper to taste\n- Olive oil\n- Fresh parsley or cilantro for garnish\n\n**Instructions:**\n1. Toast the whole grain bread slices until golden brown.\n2. Mash the ripe avocado in a bowl and season with salt and pepper.\n3. Spread the mashed avocado evenly on the toasted bread slices.\n4. Top with sliced tomatoes and a drizzle of olive oil.\n5. Garnish with fresh parsley or cilantro.\n6. Serve and enjoy!\n\n**Nutritional Facts:**\n- Calories: 350\n- Protein: 8g\n- Carbohydrates: 30g\n- Fat: 22g\n- Fiber: 10g");
        expectedOutput.put("Lunch", "Grilled Chicken Quinoa Salad\n\n**Ingredients:**\n- 1 cup cooked quinoa\n- 1 grilled chicken breast, sliced\n- 1 cup mixed greens\n- 1/2 cucumber, sliced\n- 1/2 red bell pepper, sliced\n- 1/4 red onion, thinly sliced\n- Juice of 1 lemon\n- 2 tbsp olive oil\n- Salt and pepper to taste\n\n**Instructions:**\n1. In a large bowl, combine cooked quinoa, mixed greens, cucumber, red bell pepper, and red onion.\n2. Add the grilled chicken slices on top of the salad.\n3. In a small bowl, whisk together lemon juice, olive oil, salt, and pepper to make the dressing.\n4. Drizzle the dressing over the salad and toss to combine.\n5. Serve and enjoy!\n\n**Nutritional Facts:**\n- Calories: 450\n- Protein: 30g\n- Carbohydrates: 35g\n- Fat: 20g\n- Fiber: 6g");
        expectedOutput.put("Dinner", "Lentil and Vegetable Curry\n\n**Ingredients:**\n- 1 cup dried lentils, rinsed\n- 1 can of diced tomatoes\n- 1 onion, chopped\n- 2 cloves of garlic, minced\n- 1 tsp ground cumin\n- 1 tsp ground coriander\n- 1 tsp turmeric\n- 1 tsp paprika\n- 2 cups vegetable broth\n- 1 cup mixed vegetables (carrots, bell peppers, zucchini)\n- Salt and pepper to taste\n- Fresh cilantro for garnish\n\n**Instructions:**\n1. In a large pot, sauté the chopped onion and garlic until softened.\n2. Add the ground cumin, coriander, turmeric, and paprika. Stir for 1-2 minutes until fragrant.\n3. Add the rinsed lentils, diced tomatoes, vegetable broth, and mixed vegetables to the pot.\n4. Bring the mixture to a boil, then reduce heat and simmer for 25-30 minutes until the lentils are tender.\n5. Season with salt and pepper to taste.\n6. Serve the lentil and vegetable curry garnished with fresh cilantro.\n\n**Nutritional Facts:**\n- Calories: 380\n- Protein: 20g\n- Carbohydrates: 60g\n- Fat: 5g\n- Fiber: 15g");

        // Call the method and get the actual output

        CommonUserFactory commonUserFactory = new CommonUserFactory();
        DBUserDataAccessObject dao = new DBUserDataAccessObject(commonUserFactory);
        Map<String, String> actualOutput = dao.fullMealPlan(input);

        // Assert that the actual output matches the expected output
        assertEquals(expectedOutput, actualOutput);
    }
}
