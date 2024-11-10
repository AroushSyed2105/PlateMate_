package use_case.user_profile;

public class ProfileInputData {

    private final String username;

    private final String[] dietaryRestrictions;

    private final String[] allergies;

    private final String[] healthGoals;

    private final boolean updateSuccess;

    public ProfileOutputData(String username, String[] DietaryRestrictions, String[] allergies, String[] HealthGoals, boolean updateSuccess) {
        this.username = username;
        this.allergies = allergies;
        this.healthGoals = HealthGoals;
        this.dietaryRestrictions = DietaryRestrictions;
        this.updateSuccess = updateSuccess;
    }

    public String getUsername() {

        return username;
    }

    public String[] getDietaryRestrictions() {
        return dietaryRestrictions;
    }

    public String[] getAllergies() {
        return allergies;
    }

    public String[] getHealthGoals() {
        return healthGoals;
    }

    public boolean isUpdateSuccess() {
        return updateSuccess;
    }
}