package app;

import com.cohere.api.Cohere;
import com.cohere.api.requests.ChatRequest;
import com.cohere.api.resources.v2.requests.V2ChatRequest;
import com.cohere.api.types.*;

import java.net.SocketTimeoutException;
import java.util.List;

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
                        .message("Generate a simple 1-week meal plan for someone who is allergic to dairy, nuts and Halal.")
                        .chatHistory(
                                List.of(Message.user(ChatMessage.builder().message("Generate a simple 1-week meal plan for someone who is allergic to dairy, nuts and Halal.").build()),
                                        Message.chatbot(ChatMessage.builder().message("Generate a simple 1-week meal plan for someone who is allergic to dairy, nuts and Halal.").build()))).build());

        System.out.println(response); // Prints the full JSON response

        return response.getText(); //  Returns only the text field as a String
    }

    public String getResponseRecipes(String prompt, String MealResponse) {
        NonStreamedChatResponse response = cohere.chat(
                ChatRequest.builder()
                        .message("Generate a recipe and grocery list for" + MealResponse)
                        .chatHistory(
                                List.of(Message.user(ChatMessage.builder().message("Generate a recipe and grocery list for" + MealResponse).build()),
                                        Message.chatbot(ChatMessage.builder().message("Generate a recipe and grocery list for" + MealResponse).build()))).build());

        System.out.println(response); // Prints the full JSON response

        return response.getText(); //  Returns only the text field as a String
    }


}