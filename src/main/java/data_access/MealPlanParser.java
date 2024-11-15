package data_access;
import org.json.JSONObject;

public class MealPlanParser {

    private JSONObject jsonResponse;

    public MealPlanParser(String jsonText) {
        this.jsonResponse = new JSONObject(jsonText);
    }

    // parses and print the meal plan
    public void parseAndPrintMealPlan() {
        // Access and print the meal plan data
        printMealPlan(jsonResponse);
    }

    private void printMealPlan(JSONObject mealPlanJson) {
        // ill change this after based on how many days we are asking for in our meal plan
        // can put this in loop after...
        JSONObject day1Json = mealPlanJson.getJSONObject("meal_plan").getJSONObject("day_1");
        JSONObject day2Json = mealPlanJson.getJSONObject("meal_plan").getJSONObject("day_2");

        // Access and print Day 1 meals
        printMeals("Day 1", day1Json);

        // Access and print Day 2 meals
        printMeals("Day 2", day2Json);
    }

    private void printMeals(String day, JSONObject dayJson) {
        System.out.println("Meals for " + day + ":");
        for (String mealType : new String[] {"breakfast", "lunch", "dinner", "snack"}) {
            JSONObject mealJson = dayJson.getJSONObject(mealType);
            System.out.println(" - " + mealType.toUpperCase() + ":");
            System.out.println("   Main: " + mealJson.getString("main"));
            System.out.println("   Side: " + mealJson.getString("side"));
            System.out.println("   Drink: " + mealJson.getString("drink"));
            System.out.println("   Recipe:");
            System.out.println("     Ingredients: " + mealJson.getJSONObject("recipe").getJSONArray("ingredients"));
            System.out.println("     Instructions: " + mealJson.getJSONObject("recipe").getString("instructions"));
            System.out.println();
        }
    }
}
