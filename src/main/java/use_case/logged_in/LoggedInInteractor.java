package use_case.logged_in;

import entity.Profile;
import entity.User;
import entity.UserFactory;
import interface_adapter.logged_in.LoggedInPresenter;
import use_case.signup.SignupOutputData;
import use_case.user_profile.ProfileInputData;
import use_case.user_profile.ProfileOutputData;


/**
 * The Signup Interactor.
 */
public class LoggedInInteractor implements LoggedInInputBoundary {
    private final LoggedInUserDataAccessInterface userDataAccessObject;
    private final LoggedInOutputBoundary userPresenter;
    private final UserFactory userFactory;

    public LoggedInInteractor(LoggedInUserDataAccessInterface loggedInDataAccessInterface,
                            LoggedInOutputBoundary loggedInOutputBoundary,
                            UserFactory userFactory) {
        this.userDataAccessObject = loggedInDataAccessInterface;
        this.userPresenter = loggedInOutputBoundary;
        this.userFactory = userFactory;
    }


    @Override
    public void execute(LoggedInInputData loggedInInputData) {

    }

    @Override
    public void switchToProfileView() {
        userPresenter.switchToProfileView();
    }

    @Override
    public void switchToHealthyRemindersView() { userPresenter.switchToHealthyRemindersView(); }
}
