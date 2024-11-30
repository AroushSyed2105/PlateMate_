package use_case.logged_in;

import use_case.signup.SignupOutputData;
import use_case.user_profile.ProfileOutputData;

public interface LoggedInOutputBoundary {
    void prepareSuccessView(ProfileOutputData outputData);


    void prepareFailView(String errorMessage);


    void switchToProfileView();

    void switchToHealthyRemindersView();
}

