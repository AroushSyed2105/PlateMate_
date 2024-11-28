package data_access;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import app.ChatPost; // Import ChatPost class

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import entity.User;
import entity.UserFactory;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import use_case.change_password.ChangePasswordUserDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.logout.LogoutUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;


/**
 * The DAO for user data.
 */
public class DBUserDataAccessObject implements SignupUserDataAccessInterface,
        LoginUserDataAccessInterface,
        ChangePasswordUserDataAccessInterface,
        LogoutUserDataAccessInterface {
    private static final int SUCCESS_CODE = 200;
    private static final String CONTENT_TYPE_LABEL = "Content-Type";
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String STATUS_CODE_LABEL = "status_code";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String MESSAGE = "message";
    private static final String API_KEY = "r4A0YoQcxKECMc4f2ipQT7PcKDqljAY8nYoLaETX";
    private final UserFactory userFactory;
    private final ChatPost chatPost;

    public DBUserDataAccessObject(UserFactory userFactory) {
        this.userFactory = userFactory;
        this.chatPost = new ChatPost(API_KEY); // Pass the API key for Cohere
        // No need to do anything to reinitialize a user list! The data is the cloud that may be miles away.
    }

    public String generateMealPlan(String userPreferences) {
        System.out.println("Generating meal plan based on preferences: " + userPreferences);
        return chatPost.getResponse(userPreferences); // Assumes `chatPost` is properly initialized in the constructor
    }

    public String generateMealPlanGivenGroceries(String UserGroceries, String UserFoodPreferences) {
        System.out.println("Generating meal plan based on what you have at home!");
        return chatPost.getResponseGivenGroceryList(UserGroceries,UserFoodPreferences);
    }

    public Map<String,String> tester(String planPlan) {
        System.out.println("Full meal plan based on preferences: " + planPlan);
        MealMeal planMeal = new MealMeal(planPlan);
        Map<String, String> cleanedPlan = planMeal.parseSingleDayMealPlan(planPlan);
        return cleanedPlan;
    }

    public static Map<String, Map<String, String>> formatMealDetails(Map<String, String> rawMealDetails) {
        Map<String, Map<String, String>> formattedMealDetails = new LinkedHashMap<>();

        for (Map.Entry<String, String> entry : rawMealDetails.entrySet()) {
            String mealName = entry.getKey(); // e.g., Breakfast, Lunch, Dinner
            String mealContent = entry.getValue();

            Map<String, String> sections = new LinkedHashMap<>();

            // Extract Recipe, Instructions, Grocery List, Ingredients
            String[] headers = {"Recipe", "Instructions", "Grocery List", "Ingredients"};
            for (String header : headers) {
                Pattern headerPattern = Pattern.compile("- \\*\\*" + header + ":\\*\\* (.*?)(?=\\n- |$)", Pattern.DOTALL);
                Matcher headerMatcher = headerPattern.matcher(mealContent);

                if (headerMatcher.find()) {
                    String content = headerMatcher.group(1).trim();

                    // For Instructions: split sentences into separate lines
                    if (header.equals("Instructions")) {
                        String[] sentences = content.split("\\.\\s+");
                        StringBuilder formattedInstructions = new StringBuilder();
                        for (String sentence : sentences) {
                            formattedInstructions.append("- ").append(sentence.trim()).append(".\n");
                        }
                        sections.put(header, formattedInstructions.toString().trim());
                    } else {
                        sections.put(header, content);
                    }
                }
            }

            formattedMealDetails.put(mealName, sections);
        }

        return formattedMealDetails;
    }

    public static void printFormattedMealPlan(Map<String, Map<String, String>> formattedMealDetails) {
        for (Map.Entry<String, Map<String, String>> mealEntry : formattedMealDetails.entrySet()) { // main headers - breakfast,lunch,dinner
            System.out.println("\n=======================");
            System.out.println("   " + mealEntry.getKey().toUpperCase());
            System.out.println("=======================\n");

            for (Map.Entry<String, String> sectionEntry : mealEntry.getValue().entrySet()) { // subheaders - recipe, instructions
                System.out.println("  --- " + sectionEntry.getKey() + " ---");
                System.out.println("    " + sectionEntry.getValue());
                System.out.println();
            }
        }
    }

    public Map<String,String> fullMealPlan(String planPlan) {
        System.out.println("Full meal plan based on preferences: " + planPlan);
        MealMeal planMeal = new MealMeal(planPlan);
        Map<String, String> cleanedPlan = planMeal.parseSingleDayMealPlan(planPlan);

        Map<String, String> masterMealPlan = new HashMap<>();

        // Use keys from cleanedPlan and responses from chatPost as values
        for (Map.Entry<String, String> entry : cleanedPlan.entrySet()) {
            String mealType = entry.getKey(); // "Breakfast", "Lunch", "Dinner"
            String mealDescription = entry.getValue(); // cleaned description

            // Get response for the meal description
            String response = chatPost.getResponseRecipes(mealDescription);

            // Add the key (mealType) and value (response) to masterMealPlan
            masterMealPlan.put(mealType, response);
        }

        return masterMealPlan;
    }

    // Method to generate and print the master grocery list

    public String printMasterGroceries(String userPreferences) {
        // Generate the full meal plan based on user preferences
        Map<String, String> fullMealPlan = fullMealPlan(userPreferences);

        // Create a master grocery list from the meal plan
        StringBuilder masterGroceryList = new StringBuilder();

        for (Map.Entry<String, String> entry : fullMealPlan.entrySet()) {
            String mealType = entry.getKey();
            String mealDescription = entry.getValue();

            // Extract the grocery list for this meal
            String groceryList = extractGroceryList(mealDescription);
            if (!groceryList.isEmpty()) {
                masterGroceryList.append(mealType).append(": ").append(groceryList).append("\n");
            }
        }

        return masterGroceryList.toString().trim();
    }


    // helper method to extract the grocery list from a single meal's description
    public String extractGroceryList(String mealResponse) {
        // Look for the "## Grocery List:" section in the meal response
        String[] parts = mealResponse.split("## Grocery List:", 2);
        if (parts.length > 1) {
            String grocerySection = parts[1].trim(); // Everything after "## Grocery List:"

            // Split the grocerySection into individual items by newlines or other delimiters
            String[] groceryItems = grocerySection.split("\n|-");
            StringBuilder groceryList = new StringBuilder();

            for (String item : groceryItems) {
                String trimmedItem = item.trim();
                if (!trimmedItem.isEmpty() && !trimmedItem.startsWith("##")) { // Ignore empty lines and headings
                    groceryList.append(trimmedItem).append(", ");
                }
            }

            // Remove trailing comma and space
            if (groceryList.length() > 0) {
                groceryList.setLength(groceryList.length() - 2);
            }
            return groceryList.toString();
        }

        return ""; // Return an empty string if no grocery list is found
    }

    @Override
    public User get(String username) {
        // Make an API call to get the user object.
        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        final Request request = new Request.Builder()
                .url(String.format("http://vm003.teach.cs.toronto.edu:20112/user?username=%s", username))
                .addHeader("Content-Type", CONTENT_TYPE_JSON)
                .build();
        try {
            final Response response = client.newCall(request).execute();

            final JSONObject responseBody = new JSONObject(response.body().string());

            if (responseBody.getInt(STATUS_CODE_LABEL) == SUCCESS_CODE) {
                final JSONObject userJSONObject = responseBody.getJSONObject("user");
                final String name = userJSONObject.getString(USERNAME);
                final String password = userJSONObject.getString(PASSWORD);

                return userFactory.create(name, password);
            }
            else {
                throw new RuntimeException(responseBody.getString(MESSAGE));
            }
        }
        catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void setCurrentUsername(String name) {
        // this isn't implemented for the lab
    }

    @Override
    public boolean existsByName(String username) {
        final OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        final Request request = new Request.Builder()
                .url(String.format("http://vm003.teach.cs.toronto.edu:20112/checkIfUserExists?username=%s", username))
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();
        try {
            final Response response = client.newCall(request).execute();

            final JSONObject responseBody = new JSONObject(response.body().string());

            return responseBody.getInt(STATUS_CODE_LABEL) == SUCCESS_CODE;
        }
        catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void save(User user) {
        final OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        // POST METHOD
        final MediaType mediaType = MediaType.parse(CONTENT_TYPE_JSON);
        final JSONObject requestBody = new JSONObject();
        requestBody.put(USERNAME, user.getName());
        requestBody.put(PASSWORD, user.getPassword());
        final RequestBody body = RequestBody.create(requestBody.toString(), mediaType);
        final Request request = new Request.Builder()
                .url("http://vm003.teach.cs.toronto.edu:20112/user")
                .method("POST", body)
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();
        try {
            final Response response = client.newCall(request).execute();

            final JSONObject responseBody = new JSONObject(response.body().string());

            if (responseBody.getInt(STATUS_CODE_LABEL) == SUCCESS_CODE) {
                // success!
            }
            else {
                throw new RuntimeException(responseBody.getString(MESSAGE));
            }
        }
        catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void changePassword(User user) {
        final OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        // POST METHOD
        final MediaType mediaType = MediaType.parse(CONTENT_TYPE_JSON);
        final JSONObject requestBody = new JSONObject();
        requestBody.put(USERNAME, user.getName());
        requestBody.put(PASSWORD, user.getPassword());
        final RequestBody body = RequestBody.create(requestBody.toString(), mediaType);
        final Request request = new Request.Builder()
                .url("http://vm003.teach.cs.toronto.edu:20112/user")
                .method("PUT", body)
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();
        try {
            final Response response = client.newCall(request).execute();

            final JSONObject responseBody = new JSONObject(response.body().string());

            if (responseBody.getInt(STATUS_CODE_LABEL) == SUCCESS_CODE) {
                // success!
            }
            else {
                throw new RuntimeException(responseBody.getString(MESSAGE));
            }
        }
        catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String getCurrentUsername() {
        return null;
    }
}
