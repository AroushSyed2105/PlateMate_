package data_access;

import java.util.*;
import java.util.regex.*;

// DATA PARSER FOR COHERE API

public class MealMeal {

    // Method to parse the meal plan
    public Map<String, String> parseSingleDayMealPlan(String mealPlan) {
        Map<String, String> singleDayMealPlan = new HashMap<>();

        Pattern dayPattern = Pattern.compile("\\*\\*Day 1:\\*\\*(.*?)(?=(\\*\\*Day \\d+:\\*\\*|$))", Pattern.DOTALL);
        Matcher dayMatcher = dayPattern.matcher(mealPlan);

        if (dayMatcher.find()) {
            String dayContent = dayMatcher.group(1).trim();
            String[] mealLabels = {"Breakfast", "Lunch", "Dinner"};

            for (String label : mealLabels) {
                Pattern mealPattern = Pattern.compile("- " + label + ": (.*?)(?=(\\n- |$))", Pattern.DOTALL);
                Matcher mealMatcher = mealPattern.matcher(dayContent);

                if (mealMatcher.find()) {
                    String mealDescription = mealMatcher.group(1).trim();
                    String cleanedMeal = cleanMealDescription(mealDescription);
                    singleDayMealPlan.put(label, cleanedMeal);
                }
            }
        }

        return singleDayMealPlan;
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

}
