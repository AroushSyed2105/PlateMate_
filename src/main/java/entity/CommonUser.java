package entity;

import java.util.List;

/**
 * A simple implementation of the User interface.
 */
public class CommonUser implements User {

    private final String name;
    private final String password;

    public CommonUser(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public List<String> getDietaryRestrictions() {
        return List.of();
    }

    @Override
    public List<String> getAllergies() {
        return List.of();
    }

    @Override
    public List<String> getHealthGoals() {
        return List.of();
    }

}
