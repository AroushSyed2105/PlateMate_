package interface_adapter.profile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProfileState {
    // Singleton instance
    private static ProfileState instance;

    // Private constructor to prevent instantiation
    ProfileState() {}

    // Method to get the singleton instance
    public static ProfileState getInstance() {
        if (instance == null) {
            instance = new ProfileState();
        }
        return instance;
    }

    // Instance variables
    private String username = "";
    private String loginError;
    private String password = "";
    private String[] allergies = {};
    private String[] healthGoals = {};
    private String[] dietaryRestrictions = {};

    // Getters
    public String getUsername() {
        return username;
    }

    public String getLoginError() {
        return loginError;
    }

    public String getPassword() {
        return password;
    }

    public String[] getAllergies() {
        return allergies;
    }

    public String[] getHealthGoals() {
        return healthGoals;
    }

    public String[] getDietaryRestrictions() {
        return dietaryRestrictions;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setLoginError(String loginError) {
        this.loginError = loginError;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAllergies(String[] allergies) {
        this.allergies = allergies;
    }

    public void setHealthGoals(String[] healthGoals) {
        this.healthGoals = healthGoals;
    }

    public void setDietaryRestrictions(String[] dietaryRestrictions) {
        this.dietaryRestrictions = dietaryRestrictions;
    }

    // Helper method to return a single string of all allergies
    public String getAllergiesAsString() {
        return String.join(", ", allergies);
    }

    // Helper method to return a single string of all dietary restrictions
    public String getDietaryRestrictionsAsString() {
        return String.join(", ", dietaryRestrictions);
    }

    // Method to combine all dietary information (allergies + dietary restrictions)
    public String getCombinedDietaryInfo() {
        List<String> combinedList = new ArrayList<>();
        combinedList.addAll(Arrays.asList(allergies));
        combinedList.addAll(Arrays.asList(dietaryRestrictions));
        return String.join(", ", combinedList);
    }
}
