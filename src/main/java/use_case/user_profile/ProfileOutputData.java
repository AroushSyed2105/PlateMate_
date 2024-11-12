package use_case.user_profile;

public class ProfileOutputData {

    private final String username;

    private final String[] dietaryRestrictions;

    private final String[] allergies;

    private final String[] healthGoals;

    // private final boolean updateSuccess;

    public ProfileOutputData(String[] allergies, String[] DietaryRestrictions, String[] HealthGoals,String username) {
        this.allergies = allergies;
        this.dietaryRestrictions = DietaryRestrictions;
        this.healthGoals = HealthGoals;
        this.username = username;
        // this.updateSuccess = updateSuccess;
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

    // public boolean isUpdateSuccess() {
        // return updateSuccess;
    // }
}