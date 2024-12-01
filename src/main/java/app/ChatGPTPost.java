package app;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class ChatGPTPost {
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private final String apiKey;
    private final OkHttpClient client;

    public ChatGPTPost(String apiKey) {
        this.apiKey = apiKey;
        this.client = new OkHttpClient();
        System.out.println("Using API Key: " + apiKey);  // Debug log
    }

    public String getResponse(String prompt) throws IOException {
        OkHttpClient client = new OkHttpClient();

        // Build the payload using JSONArray for "messages"
        JSONObject payload = new JSONObject();
        payload.put("model", "gpt-3.5-turbo");

        // Create the messages array3
        JSONArray messages = new JSONArray()
                .put(new JSONObject().put("role", "system").put("content", "You are a helpful assistant."))
                .put(new JSONObject().put("role", "user").put("content", prompt));
        payload.put("messages", messages);

        payload.put("temperature", 0.5);

        // Create the request body
        RequestBody body = RequestBody.create(
                MediaType.parse("application/json"), payload.toString());

        // Build the HTTP request
        Request request = new Request.Builder()
                .url(API_URL)
                .header("Authorization", "Bearer " + apiKey)
                .post(body)
                .build();

        // Execute the request and process the response
        try (Response response = client.newCall(request).execute()) {
            System.out.println("Response Code: " + response.code());
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                System.out.println("Response Body: " + responseBody);
                JSONObject jsonResponse = new JSONObject(responseBody);
                return jsonResponse.getJSONArray("choices")
                        .getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content");
            } else {
                System.err.println("API Error Code: " + response.code());
                System.err.println("Error Response Body: " + (response.body() != null ? response.body().string() : "null"));
                throw new IOException("Failed ChatGPT request: " + response.code());
            }
        }

    }

    // Get a reminder based on a prompt (using the same helper method)
    public String getReminder(String prompt) throws IOException {
        System.out.println("ChatGPTPost: Sending request with prompt: " + prompt);
        System.out.println("ChatGPTPost: Using API Key: " + apiKey);

        JSONObject payload = new JSONObject();
        payload.put("model", "gpt-3.5-turbo");
        JSONArray messages = new JSONArray()
                .put(new JSONObject().put("role", "system").put("content", "You are a helpful assistant."))
                .put(new JSONObject().put("role", "user").put("content", prompt));
        payload.put("messages", messages);
        payload.put("temperature", 0.7);

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json"), payload.toString());

        Request request = new Request.Builder()
                .url(API_URL)
                .header("Authorization", "Bearer " + apiKey)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println("Response code: " + response.code());
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                System.out.println("ChatGPTPost: Response body: " + responseBody);
                JSONObject jsonResponse = new JSONObject(responseBody);
                return jsonResponse.getJSONArray("choices")
                        .getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content");
            } else {
                System.err.println("ChatGPTPost: API Error: " + response.code());
                if (response.code() == 401) {
                    System.err.println("ChatGPTPost: Unauthorized: Check your API key or account status.");
                }
                throw new IOException("Failed ChatGPT request: " + response.code());
            }
        }
    }

//    public String getReminder(String prompt) throws IOException {
//        System.out.println("ChatGPTPost: Sending request with prompt: " + prompt);
//
//        JSONObject payload = new JSONObject();
//        payload.put("model", "gpt-3.5-turbo");
//        JSONArray messages = new JSONArray()
//                .put(new JSONObject().put("role", "system").put("content", "You are a helpful assistant."))
//                .put(new JSONObject().put("role", "user").put("content", prompt));
//        payload.put("messages", messages);
//        payload.put("temperature", 0.7);
//
//        RequestBody body = RequestBody.create(
//                MediaType.parse("application/json"), payload.toString());
//
//        Request request = new Request.Builder()
//                .url(API_URL)
//                .header("Authorization", "Bearer " + apiKey)
//                .post(body)
//                .build();
//
//        try (Response response = client.newCall(request).execute()) {
//            if (response.isSuccessful() && response.body() != null) {
//                String responseBody = response.body().string();
//                System.out.println("ChatGPTPost: Response body: " + responseBody);
//
//                JSONObject jsonResponse = new JSONObject(responseBody);
//                return jsonResponse.getJSONArray("choices")
//                        .getJSONObject(0)
//                        .getJSONObject("message")
//                        .getString("content");
//            } else {
//                System.err.println("ChatGPTPost: API Error: " + response.code());
//                if (response.code() == 401) {
//                    throw new IOException("Unauthorized: Check your API key.");
//                }
//                throw new IOException("Failed ChatGPT request: " + response.code());
//            }
//        }
//    }




//    public String getReminder(String prompt) throws IOException {
//        return getResponse(prompt);
//    }

    // Get a recipe and grocery list for a given meal
    public String getResponseRecipes(String mealResponse) throws IOException {
        String prompt = "Generate a recipe and grocery list for: " + mealResponse;
        return getResponse(prompt);
    }

    // Get a recipe, grocery list, and nutritional breakdown for a given meal
    public String getResponseNutritionalFacts(String mealResponse) throws IOException {
        String prompt = "Generate a recipe, grocery list, and nutrient breakdown for: " + mealResponse;
        return getResponse(prompt);
    }
}

//package app;
//
//import com.cohere.api.requests.ChatRequest;
//import com.cohere.api.types.ChatMessage;
//import com.cohere.api.types.Message;
//import com.cohere.api.types.NonStreamedChatResponse;
//import okhttp3.*;
//import org.json.JSONObject;
//
//import java.io.IOException;
//
//import org.json.JSONArray;
//
//import java.io.IOException;
//import java.util.List;
//
//public class ChatGPTPost {
//    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
//    private final String apiKey;
//
//    public ChatGPTPost(String apiKey) {
//        this.apiKey = apiKey;
//    }
//
//    public String getResponse(String prompt) throws IOException {
//        OkHttpClient client = new OkHttpClient();
//
//        // Build the payload using JSONArray for "messages"
//        JSONObject payload = new JSONObject();
//        payload.put("model", "gpt-3.5-turbo");
//
//        // Create the messages array
//        JSONArray messages = new JSONArray()
//                .put(new JSONObject().put("role", "system").put("content", "You are a helpful assistant."))
//                .put(new JSONObject().put("role", "user").put("content", prompt));
//        payload.put("messages", messages);
//
//        payload.put("temperature", 0.7);
//
//        // Create the request body
//        RequestBody body = RequestBody.create(
//                MediaType.parse("application/json"), payload.toString());
//
//        // Build the HTTP request
//        Request request = new Request.Builder()
//                .url(API_URL)
//                .header("Authorization", "Bearer " + apiKey)
//                .post(body)
//                .build();
//
//        // Execute the request and process the response
//        try (Response response = client.newCall(request).execute()) {
//            if (response.isSuccessful() && response.body() != null) {
//                JSONObject jsonResponse = new JSONObject(response.body().string());
//                return jsonResponse.getJSONArray("choices")
//                        .getJSONObject(0)
//                        .getJSONObject("message")
//                        .getString("content");
//            } else {
//                // Log the error response for debugging
//                if (response.body() != null) {
//                    System.err.println("Error response: " + response.body().string());
//                }
//                throw new IOException("Failed ChatGPT request: " + response.code());
//            }
//        }
//    }
//    public String getReminder(String prompt) {
//
//        return getResponse(prompt);
//    }
//
//    public String getResponseRecipes(String MealResponse) {
//        NonStreamedChatResponse response = chatGPT.chat(
//                ChatRequest.builder()
//                        .message("Generate a recipe and grocery list for" + MealResponse)
//                        .chatHistory(
//                                List.of(Message.user(ChatMessage.builder().message("Generate a recipe and grocery list for" + MealResponse).build()),
//                                        Message.chatbot(ChatMessage.builder().message("Generate a recipe and grocery list for" + MealResponse).build()))).build());
//
//        System.out.println(response); // Prints the full JSON response
//        return response.getText(); //  Returns only the text field as a String
//    }
//
//    public String getResponseNutritionalFacts(String MealResponse) {
//        NonStreamedChatResponse response = cohere.chat(
//                ChatRequest.builder()
//                        .message("Generate a recipe and grocery list and nutrient breakdown for" + MealResponse)
//                        .chatHistory(
//                                List.of(Message.user(ChatMessage.builder().message("Generate a recipe and grocery list and nutrient breakdown for" + MealResponse).build()),
//                                        Message.chatbot(ChatMessage.builder().message("Generate a recipe and grocery list and nutrient breakdown for" + MealResponse).build()))).build());
//
//        // System.out.println(response); // Prints the full JSON response
//        return response.getText(); //  Returns only the text field as a String
//    }
//}
//

