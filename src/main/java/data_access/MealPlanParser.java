package data_access;
import org.json.JSONObject;

// FOR CHATGPT API

public class MealPlanParser {

    private JSONObject jsonResponse;

    // Instance variables to store meal details

    private String main;
    private String side;
    private String drink;
    private String ingredients;
    private String instructions;

    public MealPlanParser(String jsonText) {
        this.jsonResponse = new JSONObject(jsonText);
    }

    public void parseAndPrintMealPlan() {
        printMealPlan(jsonResponse);
    }

    private void printMealPlan(JSONObject mealPlanJson) {
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
            main = mealJson.getString("main");
            side = mealJson.getString("side");
            drink = mealJson.getString("drink");
            ingredients = mealJson.getJSONObject("recipe").getJSONArray("ingredients").toString();
            instructions = mealJson.getJSONObject("recipe").getString("instructions");

            System.out.println(" - " + mealType.toUpperCase() + ":");
            System.out.println("   Main: " + main);
            System.out.println("   Side: " + side);
            System.out.println("   Drink: " + drink);
            System.out.println("   Recipe:");
            System.out.println("     Ingredients: " + ingredients);
            System.out.println("     Instructions: " + instructions);
            System.out.println();
        }
    }

    // Getter methods to retrieve meal details
    public String getMain() {return main;}
    public String getSide() {return side;}
    public String getDrink() {return drink;}
    public String getIngredients() {return ingredients;}
    public String getInstructions() {return instructions;}
}
