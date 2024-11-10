package use_case.user_profile;

import entity.Profile;
import entity.User;

public interface ProfileUserDataAccessInterface {

    /**
     * Checks if the given username exists.
     * @param username the username to look for
     * @return true if a user with the given username exists; false otherwise
     */
    boolean existsByUsername(String username);

    User getProfileByUsername(String username);

    /**
     * Saves the profile.
     * @param profile saves the users profile
     */
    void saveProfile(Profile profile);

    void deleteProfile(String username);
}