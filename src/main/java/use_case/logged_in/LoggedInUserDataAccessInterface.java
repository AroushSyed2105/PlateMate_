package use_case.logged_in;

import entity.User;

public interface LoggedInUserDataAccessInterface {

    boolean existsByName(String username);

    /**
     * Saves the user.
     *
     * @param user the user to save
     */
    void save(User user);

    /**
     * Returns the user with the given username.
     *
     * @param username the username to look up
     * @return the user with the given username
     */
    User get(String username);

    /**
     * Returns the username of the curren user of the application.
     *
     * @return the username of the current user; null indicates that no one is logged into the application.
     */
    String getCurrentUsername();
}


