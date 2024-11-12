package entity;

/**
 * Factory for creating CommonUser objects.
 */
public class CommonUserFactory implements UserFactory, ProfileFactory {

    @Override
    public User create(String name, String password) {
        return new CommonUser(name, password);
    }

    @Override
    public Profile create(String[] allergies, String[] DietaryRestrictions, String[] HealthGoals, String username) {
        return null;
    }
}
