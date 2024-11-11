package interface_adapter.profile;

import interface_adapter.ViewManagerModel;
import interface_adapter.change_password.LoggedInState;
import interface_adapter.change_password.LoggedInViewModel;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import use_case.user_profile.ProfileOutputBoundary;
import use_case.user_profile.ProfileOutputData;

/**
 * The Presenter for the Login Use Case.
 */
public class ProfilePresenter implements ProfileOutputBoundary {
    private final ProfileViewModel viewModel;
    private final LoggedInViewModel loggedInViewModel;
    private final MealPlanViewModel meanPlanViewModel;
    private final ViewManagerModel viewManagerModel;

    public ProfilePresenter(ProfileViewModel viewModel, ViewManagerModel viewManagerModel,
                            LoggedInViewModel loggedInViewModel,
                            MealPlanViewModel mealPlanViewModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
        this.meanPlanViewModel = mealPlanViewModel;
    }

    @Override
    public void prepareSuccessView(ProfileOutputData response) {
        // On success, switch to the meal plan view.
        this.viewManagerModel.setState(meanPlanViewModel.getViewName());
    }

    @Override
    public void prepareFailView(String error) {
        final LoginState loginState = loginViewModel.getState();
        loginState.setLoginError(error);
        loginViewModel.firePropertyChanged();
    }
}

