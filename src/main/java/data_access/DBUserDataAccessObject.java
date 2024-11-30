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

    public static Map<String, Map<String, String>> parseMealDetails(Map<String, String> mealPlan) {
        Map<String, Map<String, String>> parsedPlan = new LinkedHashMap<>();

        for (Map.Entry<String, String> mealEntry : mealPlan.entrySet()) {
            String mealType = mealEntry.getKey();
            String mealDetails = mealEntry.getValue();

            Map<String, String> subheaderMap = new LinkedHashMap<>();
            Pattern subheaderPattern = Pattern.compile("\\*\\*(Ingredients|Instructions|Nutritional Facts):\\*\\*(.*?)((?=\\*\\*(Ingredients|Instructions|Nutritional Facts):)|$)", Pattern.DOTALL);
            Matcher subheaderMatcher = subheaderPattern.matcher(mealDetails);

            while (subheaderMatcher.find()) {
                String subheader = subheaderMatcher.group(1).trim();
                String content = subheaderMatcher.group(2).trim();
                subheaderMap.put(subheader, content);
            }

            parsedPlan.put(mealType, subheaderMap);
        }

        return parsedPlan;
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
    public static Map<String, String> fullMealPlan(String input) {
        Map<String, String> mealMap = new LinkedHashMap<>();
        Pattern pattern = Pattern.compile("\\*\\*(Breakfast|Lunch|Dinner):\\s*(.*?)(?=\\n\\*\\*(Breakfast|Lunch|Dinner):|$)\n", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String mealType = matcher.group(1).trim();
            String mealDetails = matcher.group(2).trim().replaceAll("\\*\\*", "");
            mealMap.put(mealType, mealDetails);
        }
        return mealMap;
    }

    // helper method to extract the grocery list from a single meal's description
    public List<String> extractGroceryList(Map<String, String> mealPlanMap) {
        Set<String> grocerySet = new HashSet<>();

        for (String details : mealPlanMap.values()) {
            // Find the "Ingredients" section in the meal details
            if (details.contains("**Ingredients:**")) {
                String[] parts = details.split("\\*\\*Ingredients:\\*\\*", 2);
                if (parts.length > 1) {
                    String ingredientsSection = parts[1].split("\\*\\*", 2)[0].trim(); // Extract only the ingredients section
                    // Split the ingredients into individual items and add them to the set
                    String[] ingredients = ingredientsSection.split("\n");
                    for (String ingredient : ingredients) {
                        ingredient = ingredient.replaceFirst("- ", "").trim(); // Remove leading "- " and extra spaces
                        if (!ingredient.isEmpty()) {
                            grocerySet.add(ingredient);
                        }
                    }
                }
            }
        }

        // Convert the set to a sorted list for final output
        List<String> groceryList = new ArrayList<>(grocerySet);
        Collections.sort(groceryList); // Optional: Sort alphabetically
        return groceryList;
    }

    public List<Integer> extractCalories(String planPlan) {
        //MealMeal object
        MealMeal planMeal = new MealMeal(planPlan);

        //The meal descriptions from the full meal plan
//        List<String> mealDescriptions = new ArrayList<>(fullMealPlan(planPlan).values());
//        System.out.println("mealDescription" + mealDescriptions);

        List<String> mealDescriptions = List.of("Recipe: Oatmeal with Fruits\n\n" +
                "Ingredients:\n" +
                "- Oats\n- Banana\n- Almonds\n- Honey\n- Chia Seeds\n\n" +
                "Nutrient Breakdown (per serving):\n" +
                "- Calories: 300\n- Carbohydrates: 50g\n- Fat: 10g\n- Protein: 5g\n- Fiber: 7g",

        "Recipe: Grilled Chicken Salad\n\n" +
                "Ingredients:\n" +
                "- Chicken Breast\n- Mixed Greens\n- Tomatoes\n- Cucumber\n- Olive Oil\n- Lemon Juice\n\n" +
                "Nutrient Breakdown (per serving):\n" +
                "- Calories: 350\n- Carbohydrates: 10g\n- Fat: 15g\n- Protein: 40g\n- Fiber: 4g",

                "Recipe: Creamy Avocado Pasta (Vegan)\n\n" +
                        "Ingredients:\n" +
                        "- 12 oz pasta\n- 2 ripe avocados\n- Fresh basil\n- Garlic\n- Olive oil\n- Nutritional yeast\n\n" +
                        "Nutrient Breakdown (per serving):\n" +
                        "- Calories: 550\n- Carbohydrates: 70g\n- Fat: 28g\n- Protein: 12g\n- Fiber: 6g");


        //Extract the calories using extractCalories
        List<Integer> plannedCalories = planMeal.extractCalories(mealDescriptions);
        System.out.println("plannedCalories"+ plannedCalories);

        //Record Actual calories using recordActualCalories
        List<Integer> actualCalories = planMeal.recordActualCalories(plannedCalories);
        System.out.println("actualCalories" + actualCalories);

        //Display calorie progress using displayCalorieProgress
        planMeal.displayCalorieProgress(plannedCalories, actualCalories);

        //Return the list of calories for each meal
        return plannedCalories;
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




