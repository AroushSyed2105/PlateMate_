package entity;
/**
 *  An implementation of the Profile interface
 */

public class UserProfile implements Profile {
    private final String username;
    private final String[] dietaryRestrictions;
    private final String[] allergies;
    private final String[] healthGoals;

    public UserProfile(String username, String[] dietaryRestrictions, String[] allergies, String[] healthGoals) {
        this.username = username;
        this.dietaryRestrictions = dietaryRestrictions;
        this.allergies = allergies;
        this.healthGoals = healthGoals;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String[] getDietaryRestrictions() {
        return dietaryRestrictions;
    }

    @Override
    public String[] getAllergies() {
        return allergies;
    }

    @Override
    public String[] getHealthGoals() {
        return healthGoals;
    }
}

