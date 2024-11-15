package entity;

import java.util.List;

/**
 * A simple implementation of the User interface.
 */
public class CommonUser implements User {

    private final String name;
    private final String password;
    private final Profile profile;

    public CommonUser(String name, String password, Profile profile) {
        this.name = name;
        this.password = password;
        this.profile = profile;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public Profile getProfile() {
        return profile;
    }

    }
