package data_access;

import java.util.*;
import java.util.regex.*;

// DATA PARSER FOR COHERE API

public class MealMeal {

    private final String mealPlan;

    // Constructor to initialize the MealPlanParser with a meal plan string
    public MealMeal(String mealPlan) {
        this.mealPlan = mealPlan;
    }

    // Method to parse the meal plan
    public Map<Integer, List<String>> parseMealPlan() {
        Map<Integer, List<String>> mealPlanMap = new HashMap<>();

        // Split the meal plan by days (Day 1, Day 2, ..., Day 7)
        Pattern dayPattern = Pattern.compile("\\*\\*Day (\\d+):\\*\\*(.*?)(?=(\\*\\*Day \\d+:\\*\\*|$))", Pattern.DOTALL); // "Matcher" interprets "Pattern" and performs match operations on a given input string.
        Matcher dayMatcher = dayPattern.matcher(mealPlan);
        // togetherrrr "Pattern" and "Matcher" are used to search for patterns in text.

        while (dayMatcher.find()) {  //  we iterating over all matches of the day pattern in the mealPlan string.
            int day = Integer.parseInt(dayMatcher.group(1));// extracts  day number using dayMatcher.group(1) and converts it to an int.
            String dayContent = dayMatcher.group(2).trim(); // extracts ENTIRE meal content for  day using dayMatcher.group(2) and trims any extra whitespace.
            // group() method used to get specfic parts of matched pattern

            // split by meals (Breakfast, Lunch, Dinner)
            List<String> meals = new ArrayList<>();
            String[] mealLabels = {"Breakfast", "Lunch", "Dinner"};

            for (String label : mealLabels) {
                Pattern mealPattern = Pattern.compile("- " + label + ": (.*?)(?=(\\n- |$))", Pattern.DOTALL);
                Matcher mealMatcher = mealPattern.matcher(dayContent);

                // - " + label + ": (.*?): Matches "- Breakfast: ...", "- Lunch: ...", and "- Dinner: ...".
                //(?=(\\n- |$)): A lookahead to stop at the next meal or the end of the day's content.
                // Pattern.DOTALL: Ensures that . matches newlines.

                if (mealMatcher.find()) { // Checks if a meal pattern is found within dayContent.
                    String mealDescription = mealMatcher.group(1).trim(); //  extracts meal description using mealMatcher.group(1) and trims whitespace.
                    String cleanedMeal = cleanMealDescription(mealDescription); //  Adds the cleaned meal description to the meals list.
                    meals.add(cleanedMeal);
                }
            }

            mealPlanMap.put(day, meals); // adds current day's meals to mealPlanMap with the day number as the key.
        }

        return mealPlanMap;
    }

    // Method to clean up meal descriptions by removing filler words
    public static String cleanMealDescription(String description) {
        String[] fillerWords = {"with", "that", "this", "or","an","by", "served", "and", "a", "of", "on", "top", "topped", "made", "using"};
        List<String> keyIngredients = new ArrayList<>();

        // Split the description into individual words and filter out filler words
        String[] words = description.split(" ");
        for (String word : words) {
            if (!Arrays.asList(fillerWords).contains(word.toLowerCase())) {
                keyIngredients.add(word);
            }
        }

        return String.join(" ", keyIngredients);
    }

    public Map<Integer, List<String>> extractGroceryLists(Map<Integer, List<String>> recipesMap) {
        Map<Integer, List<String>> groceryListsMap = new HashMap<>();

        recipesMap.forEach((day, meals) -> {
            List<String> groceriesForDay = new ArrayList<>();

            for (String mealResponse : meals) {
                // Split the meal response at "Instructions"
                String[] parts = mealResponse.split("Instructions", 2); // will be split into 2 things; before "Instructions" and AFTER instructions
                if (parts.length > 0) { //incase API call is stupid and doesnt give us any grocery stuff
                    String grocerySection = parts[0].trim();  // remooving white spaces

                    // Split the grocerySection by commas or other delimiters to get individual items
                    String[] groceryItems = grocerySection.split(",|and|-"); // Splits by commas or "and" or -
                    for (String item : groceryItems) {
                        item = item.trim(); // remooving white spaces
                        groceriesForDay.add(item);
                    }
                }
            }

            // Add the list of groceries for the current day to the map
            groceryListsMap.put(day, groceriesForDay);
        });

        return groceryListsMap;
    }

    public List<List<String>> extractNutrients(List<String> mealResponses) {
        // using lists as it's just for a single day, instead of multiple
        List<List<String>> nutrientsForMeals = new ArrayList<>();

        for (String mealResponse : mealResponses) {
            List<String> nutrients = new ArrayList<>();

            // matching each of the nutrients individually
            Pattern nutrientPattern = Pattern.compile(
                    "(Calories:.*?\\d+\\s*\\w*?)|" +
                            "(Carbohydrates:.*?\\d+\\s*\\w*?)|" +
                            "(Fat:.*?\\d+\\s*\\w*?)|" +
                            "(Protein:.*?\\d+\\s*\\w*?)|" +
                            "(Fiber:.*?\\d+\\s*\\w*?)|" +
                            "(Vitamins:.*?\\w.*?)|" +
                            "(Minerals:.*?\\w.*?)",
                    Pattern.DOTALL
            );

            Matcher nutrientMatcher = nutrientPattern.matcher(mealResponse);

            while (nutrientMatcher.find()) {
                // add each matched nutrient to the list
                String nutrient = nutrientMatcher.group().trim();

                // add "g" for specific nutrients (Carbohydrates, Fat, Protein, and Fiber) to represent "grams"
                if (nutrient.startsWith("Carbohydrates") || nutrient.startsWith("Fat") ||
                        nutrient.startsWith("Protein") || nutrient.startsWith("Fiber")) {
                    nutrient = nutrient + " g";
                }

                nutrients.add(nutrient);
            }

            // if no nutrient data is found: (shouldn't run into this case since we're the ones making the api call but should we include this just in case?
            if (nutrients.isEmpty()) {
                nutrients.add("Nutrient information not found.");
            }

            // adding the nutrients for the meal to the list of all nutrients
            nutrientsForMeals.add(nutrients);
        }

        return nutrientsForMeals;
    }


    public List<Integer> extractCalories(List<String> mealResponses) {
        List<Integer> caloriesForDay = new ArrayList<>();

        for (String mealResponse : mealResponses) {
            int calories = 0;

            // Extract the "Calories" value from the nutrient section
            Pattern caloriesPattern = Pattern.compile("Calories:\\s*\\D*(\\d+)", Pattern.DOTALL); // Matches "Calories: [number]"
            Matcher matcher = caloriesPattern.matcher(mealResponse);

            if (matcher.find()) {
                calories = Integer.parseInt(matcher.group(1)); // Extract only the numeric value
            }

            //Add the extracted value to the calories List
            caloriesForDay.add(calories);
        }

        return caloriesForDay;
    }


    public List<Integer> recordActualCalories(List<Integer> plannedCalories) {
        List<Integer> actualCalories = new ArrayList<>();
        //Allowing for user input
        Scanner scanner = new Scanner(System.in);

        String[] mealLabels = {"Breakfast", "Lunch", "Dinner"};

        for (int i = 0; i < plannedCalories.size(); i++) {
            int planned = plannedCalories.get(i);
            System.out.println(mealLabels[i] + " - Planned Calories: " + planned);
            System.out.print("Enter actual calories consumed for " + mealLabels[i] + ": ");
            int actual = scanner.nextInt();
            actualCalories.add(actual);
        }

        return actualCalories;
    }

    //display calorie tracking progress for a single day
    public void displayCalorieProgress(List<Integer> plannedCalories, List<Integer> actualCalories) {
        System.out.println("\nCalorie Tracking Progress (Single Day):");

        String[] mealLabels = {"Breakfast", "Lunch", "Dinner"};
        int plannedTotal = 0;
        int actualTotal = 0;

        for (int i = 0; i < plannedCalories.size(); i++) {
            int planned = plannedCalories.get(i); //calculated from api generated information
            int actual = actualCalories.get(i); //actual value inputted by usera

            System.out.println(mealLabels[i] + ": Planned = " + planned + " kcal, Actual = " + actual + " kcal");

            plannedTotal += planned;
            actualTotal += actual;
        }

        System.out.println("Total: Planned = " + plannedTotal + " kcal, Actual = " + actualTotal + " kcal");
    }


}
