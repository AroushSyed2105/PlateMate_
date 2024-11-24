package app;

import com.cohere.api.Cohere;
import com.cohere.api.requests.ChatRequest;
import com.cohere.api.types.*;
import data_access.MealMeal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatPost {
    private final Cohere cohere;

    public ChatPost(String apiKey) {
        this.cohere = Cohere.builder()
                .token(apiKey)
                .clientName("PlateMate")
                .build();
    }

    public String getResponse(String prompt) {
        NonStreamedChatResponse response = cohere.chat(
                ChatRequest.builder()
                        .message("Generate a simple 1 day meal plan for someone with these restrictions" + prompt)
                        .chatHistory(
                                List.of(Message.user(ChatMessage.builder().message("Generate a simple 1 day meal plan for someone with these restrictions" + prompt).build()),
                                        Message.chatbot(ChatMessage.builder().message("Generate a simple 1 day meal plan for someone with these restrictions" + prompt).build()))).build());

        System.out.println(response); // Prints the full JSON response
        return response.getText(); //  Returns only the text field as a String
    }

    public String getResponseRecipes(String MealResponse) {
        NonStreamedChatResponse response = cohere.chat(
                ChatRequest.builder()
                        .message("Generate a recipe and grocery list for" + MealResponse)
                        .chatHistory(
                                List.of(Message.user(ChatMessage.builder().message("Generate a recipe and grocery list for" + MealResponse).build()),
                                        Message.chatbot(ChatMessage.builder().message("Generate a recipe and grocery list for" + MealResponse).build()))).build());

        System.out.println(response); // Prints the full JSON response
        return response.getText(); //  Returns only the text field as a String
    }

    public String getResponseGivenGroceryList(String UseriIngrediants, String UserPreferences) {
        NonStreamedChatResponse response = cohere.chat(
                ChatRequest.builder()
                        .message("Generate a simple 1 day meal plan for someone with these ingredients " + UseriIngrediants + "and preferences " + UserPreferences)
                        .chatHistory(
                                List.of(Message.user(ChatMessage.builder().message("Generate a simple 1 day meal plan for someone with these ingredients " + UseriIngrediants + "and preferences " + UserPreferences).build()),
                                        Message.chatbot(ChatMessage.builder().message("Generate a simple 1 day meal plan for someone with these ingredients " + UseriIngrediants + "and preferences " + UserPreferences).build()))).build());

        System.out.println(response); // Prints the full JSON response
        return response.getText(); //  Returns only the text field as a String
    }

    public Map<String, String> getRecipes(String mealPlan) {
        MealMeal mealMeal = new MealMeal();
        Map<String, String> parsedMealPlan = mealMeal.parseSingleDayMealPlan(mealPlan);

        // Map to store the API responses for each meal type
        Map<String, String> recipesMap = new HashMap<>();

        // Loop through the parsed meal plan and call the API for each meal
        parsedMealPlan.forEach((mealType, mealDescription) -> {
            // Get the API response for the current meal description
            String recipeResponse = getResponseRecipes(mealDescription);
            // Store the API response in the recipesMap
            recipesMap.put(mealType, recipeResponse);
        });

        return recipesMap;
    }

}