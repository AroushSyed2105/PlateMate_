package entity;

/**
 * The representation of a user in our program.
 */
public interface Profile {

    /**
     * Returns the username of the user.
     * @return the username of the user.
     */
    String getUsername();

    String[] getDietaryRestrictions();

    String[] getAllergies();

    String[] getHealthGoals();
}
