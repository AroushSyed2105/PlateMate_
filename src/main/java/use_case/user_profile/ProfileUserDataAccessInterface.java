package use_case.user_profile;

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
     * Saves the user.
     * @param user the user to save
     */
    void saveProfile(User user);

    void deleteProfile(String username);
}