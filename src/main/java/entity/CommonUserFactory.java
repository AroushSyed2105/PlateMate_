package entity;

/**
 * Factory for creating CommonUser objects.
 */
public class CommonUserFactory implements UserFactory, ProfileFactory {

    @Override
    public User create(String name, String password) {
        Profile profile = new UserProfile(name, new String[0], new String[0], new String[0]);
        return new CommonUser(name, password, profile);
    }

    @Override
    public Profile create(String[] allergies, String[] dietaryRestrictions, String[] healthGoals, String username) {
        return new UserProfile(username, dietaryRestrictions, allergies, healthGoals);
    }
}
