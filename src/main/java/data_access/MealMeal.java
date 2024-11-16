package data_access;

import java.util.*;
import java.util.regex.*;

public class MealMeal {

    private String mealPlan;

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

    // Method to display the parsed meal plan
    public void displayMealPlan() {
        Map<Integer, List<String>> parsedMealPlan = parseMealPlan();

        parsedMealPlan.forEach((day, meals) -> {
            System.out.println("Day " + day + ":");
            meals.forEach(System.out::println);
            System.out.println();
        });
    }
}
