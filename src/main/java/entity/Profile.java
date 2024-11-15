package entity;

/**
 * The representation of a profile in our program.
 */
public interface Profile {

    /**
     * Returns the username of the user.
     * @return the username of the user.
     */
    String getUsername();

    /**
     * Returns the dietary restrictions of the user.
     * @return the dietary restrictions of the user.
     */
    String[] getDietaryRestrictions();

    /**
     * Returns the allergies of the user.
     * @return the allergies of the user.
     */
    String[] getAllergies();

    /**
     * Returns the health goals of the user.
     * @return the health goals of the user.
     */
    String[] getHealthGoals();
}
