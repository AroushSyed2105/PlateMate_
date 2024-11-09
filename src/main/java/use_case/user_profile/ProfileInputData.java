package use_case.user_profile;

/**
 * The Input Data for the Profile Use Case.
 */

public class ProfileInputData {
    private final String[] allergies;
    private final String[] HealthyGoals ;
    private final String[] DietaryRestrictions;

    public ProfileInputData(String[] allergies, String[] HealthyGoals, String[] DietaryRestrictions) {
        this.allergies = allergies;
        this.HealthyGoals = HealthyGoals;
        this.DietaryRestrictions = DietaryRestrictions;
    }

    String[] getAllergies() { return allergies; }
    String[] getHealthyGoals() { return HealthyGoals; }
    String[] getDietaryRestrictions() { return DietaryRestrictions; }
}
