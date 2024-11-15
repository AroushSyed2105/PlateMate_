package interface_adapter.logged_in;

import interface_adapter.ViewManagerModel;
import interface_adapter.change_password.LoggedInViewModel;
import interface_adapter.profile.ProfileState;
import interface_adapter.profile.ProfileViewModel;
import use_case.logged_in.LoggedInOutputBoundary;
import use_case.user_profile.ProfileOutputData;

public class LoggedInPresenter implements LoggedInOutputBoundary {
    private LoggedInViewModel loggedInViewModel;
    private ViewManagerModel viewManagerModel;
    private ProfileViewModel profileViewModel;

    public LoggedInPresenter(ViewManagerModel viewManagerModel,
                             LoggedInViewModel loggedInViewModel,
                             ProfileViewModel profileViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
        this.profileViewModel = profileViewModel;

    }

    public void prepareSuccessView(ProfileOutputData response) {
        // We need to switch to the profile view, which should have
        // the current user, and the Profile attributes.

        // We also need to set the username in the LoggedInState to
        // the empty string.

        // have prepareSuccessView update the LoggedInState

        // 1. get the ProfileState out of the appropriate View Model,
        final ProfileState profileState = profileViewModel.getState();

        // 2. set the username in the state to the empty string
        profileState.setUsername(response.getUsername());
        profileState.setAllergies(response.getAllergies());
        profileState.setHealthGoals(response.getHealthGoals());
        profileState.setDietaryRestrictions(response.getDietaryRestrictions());

        profileViewModel.setState(profileState);
        // 4. firePropertyChanged so that the View that is listening is updated.
        profileViewModel.firePropertyChanged();

        // have prepareSuccessView update the LoginState

        // 5. get the LoginState out of the appropriate View Model,


        // This code tells the View Manager to switch to the LoginView.
        this.viewManagerModel.setState(profileViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {

    }

    @Override
    public void switchToProfileView() {
        viewManagerModel.setState(profileViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
