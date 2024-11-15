package entity;

/**
 * Factory for creating profiles.
 */
public interface ProfileFactory {
    /**
     * Creates a new Profile.
     * @param allergies the allergies of the user
     * @param dietaryRestrictions eating/health restrictions
     * @param healthGoals healthy habits
     * @param username the name of user
     * @return the user's profile
     */
    Profile create(String[] allergies, String[] dietaryRestrictions, String[] healthGoals, String username);

}
