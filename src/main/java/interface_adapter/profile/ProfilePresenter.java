package interface_adapter.profile;

import interface_adapter.ViewManagerModel;
import interface_adapter.change_password.LoggedInState;
import interface_adapter.change_password.LoggedInViewModel;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import interface_adapter.meal_plan.MealPlanViewModel;
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

        final LoggedInState loggedInState = loggedInViewModel.getState();

        // 2. set the username in the state to the empty string
        loggedInState.setUsername("");
        // 3. set the state in the LoggedInViewModel to the updated state
        loggedInViewModel.setState(loggedInState);
        // 4. firePropertyChanged so that the View that is listening is updated.
        loggedInViewModel.firePropertyChanged();

        this.viewManagerModel.setState(meanPlanViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        // No need to add code here. We'll assume that profile can't fail.
        // Thought question: is this a reasonable assumption?
    }
}

