package app;

import com.cohere.api.Cohere;
import com.cohere.api.requests.ChatRequest;
import com.cohere.api.resources.v2.requests.V2ChatRequest;
import com.cohere.api.types.*;
import data_access.MealMeal;

import java.net.SocketTimeoutException;
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
        try {
            // Make the Cohere API call with the provided prompt
            NonStreamedChatResponse response = cohere.chat(
                    ChatRequest.builder()
                            .message(prompt) // Use the dynamic prompt
                            .chatHistory(
                                    List.of(Message.user(
                                            ChatMessage.builder().message(prompt).build()
                                    ))
                            ).build()
            );

            // Log the full response for debugging
            System.out.println(response);

            // Return the response text
            return response.getText();
        } catch (Exception e) {
            // Log the error for debugging purposes
            System.err.println("Error in ChatPost.getResponse(): " + e.getMessage());
            e.printStackTrace();
            // Return null or an appropriate error message
            return null;
        }
    }


    //    public String getResponse(String prompt) {
//        NonStreamedChatResponse response = cohere.chat(
//                ChatRequest.builder()
//                        .message("Generate a simple 1 day meal plan for someone who is allergic to dairy, nuts and Halal.")
//                        .chatHistory(
//                                List.of(Message.user(ChatMessage.builder().message("Generate a simple 1-week meal plan for someone who is allergic to dairy, nuts and Halal.").build()),
//                                        Message.chatbot(ChatMessage.builder().message("Generate a simple 1-week meal plan for someone who is allergic to dairy, nuts and Halal.").build()))).build());
//
//        System.out.println(response); // Prints the full JSON response
//        return response.getText(); //  Returns only the text field as a String
//    }
    public String getReminder(String prompt) {

        return getResponse(prompt);
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

    public String getResponseNutritionalFacts(String MealResponse) {
        NonStreamedChatResponse response = cohere.chat(
                ChatRequest.builder()
                        .message("Generate a recipe and grocery list and nutrient breakdown for" + MealResponse)
                        .chatHistory(
                                List.of(Message.user(ChatMessage.builder().message("Generate a recipe and grocery list and nutrient breakdown for" + MealResponse).build()),
                                        Message.chatbot(ChatMessage.builder().message("Generate a recipe and grocery list and nutrient breakdown for" + MealResponse).build()))).build());

        System.out.println(response); // Prints the full JSON response
        return response.getText(); //  Returns only the text field as a String
    }
}