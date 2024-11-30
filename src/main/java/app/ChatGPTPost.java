package app;

import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;

import org.json.JSONArray;

import java.io.IOException;

public class ChatGPTPost {
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private final String apiKey;

    public ChatGPTPost(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getChatGPTResponse(String prompt) throws IOException {
        OkHttpClient client = new OkHttpClient();

        // Build the payload using JSONArray for "messages"
        JSONObject payload = new JSONObject();
        payload.put("model", "gpt-3.5-turbo");

        // Create the messages array
        JSONArray messages = new JSONArray()
                .put(new JSONObject().put("role", "system").put("content", "You are a helpful assistant."))
                .put(new JSONObject().put("role", "user").put("content", prompt));
        payload.put("messages", messages);

        payload.put("temperature", 0.7);

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
            if (response.isSuccessful() && response.body() != null) {
                JSONObject jsonResponse = new JSONObject(response.body().string());
                return jsonResponse.getJSONArray("choices")
                        .getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content");
            } else {
                // Log the error response for debugging
                if (response.body() != null) {
                    System.err.println("Error response: " + response.body().string());
                }
                throw new IOException("Failed ChatGPT request: " + response.code());
            }
        }
    }
}


