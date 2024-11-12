package use_case.user_profile;

/**
 * The Input Data for the Profile Use Case.
 */

public class ProfileInputData {
    private final String[] allergies;
    private final String[] healthGoals ;
    private final String[] dietaryRestrictions;
    private final String username;

    public ProfileInputData(String[] allergies, String[] HealthGoals, String[] DietaryRestrictions, String username) {
        this.allergies = allergies;
        this.healthGoals = HealthGoals;
        this.dietaryRestrictions = DietaryRestrictions;
        this.username = username;
    }

    String[] getAllergies() { return allergies; }
    String[] getHealthGoals() { return healthGoals; }
    String[] getDietaryRestrictions() { return dietaryRestrictions; }
    String getUsername() { return username; }
}
