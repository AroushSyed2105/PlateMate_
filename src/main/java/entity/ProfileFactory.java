package entity;

/**
 * Factory for creating users.
 */
public interface ProfileFactory {
    /**
     * Creates a new Profile.
     * @param allergies the allergies of the user
     * @param DietaryRestrictions eating/health restrictions
     * @param HealthGoals healthy habits
     * @param username the name of user
     * @return the user's profile
     */
    Profile create(String[] allergies, String[] DietaryRestrictions, String[] HealthGoals, String username);

}
