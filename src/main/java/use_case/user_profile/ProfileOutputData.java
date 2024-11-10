package use_case.user_profile;

import java.util.List;

public class ProfileOutputData {

    private final String username;

    private final List<String> dietaryRestrictions;

    private final List<String> allergies;

    private final List<String> healthGoals;

    private final boolean updateSuccess;

    public ProfileOutputData(String username, List<String> dietaryRestrictions, List<String> allergies, List<String> healthGoals, boolean updateSuccess) {
        this.username = username;
        this.dietaryRestrictions = dietaryRestrictions;
        this.allergies = allergies;
        this.healthGoals = healthGoals;
        this.updateSuccess = updateSuccess;
    }

    public String getUsername() {

        return username;
    }

    public List<String> getDietaryRestrictions() {
        return dietaryRestrictions;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public List<String> getHealthGoals() {
        return healthGoals;
    }

    public boolean isUpdateSuccess() {
        return updateSuccess;
    }
}