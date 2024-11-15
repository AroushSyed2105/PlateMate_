package use_case.logged_in;

import use_case.signup.SignupInputData;

public interface LoggedInInputBoundary {

    /**
     * Executes the signup use case.
     * @param loggedInInputData the input data
     */
    void execute(LoggedInInputData loggedInInputData);

    /**
     * Executes the switch to login view use case.
     */
    void switchToProfileView();
}
