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
    private final ProfileViewModel profileViewModel;
    private final LoggedInViewModel loggedInViewModel;
    private final MealPlanViewModel meanPlanViewModel;
    private final ViewManagerModel viewManagerModel;

    public ProfilePresenter(ViewManagerModel viewManagerModel, LoggedInViewModel loggedInViewModel,
                            MealPlanViewModel mealPlanViewModel,
                            ProfileViewModel profileViewModel
                            ) {
        this.profileViewModel = profileViewModel;
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
        this.meanPlanViewModel = mealPlanViewModel;
    }

    @Override
    public void prepareSuccessView(ProfileOutputData response) {
        // On success, switch to the meal plan view.

        //final LoggedInState loggedInState = loggedInViewModel.getState();
        final ProfileState profileState = profileViewModel.getState();

//        //not sure what purpose this block is serving right now
//        // 2. set the username in the state to the empty string
//        loggedInState.setUsername("");
//        // 3. set the state in the LoggedInViewModel to the updated state
//        loggedInViewModel.setState(loggedInState);
//        // 4. firePropertyChanged so that the View that is listening is updated.
//        loggedInViewModel.firePropertyChanged();

        //Update the profile information
        profileState.setUsername(response.getUsername());
        profileState.setAllergies(response.getAllergies());
        profileState.setHealthGoals(response.getHealthGoals());
        profileState.setDietaryRestrictions(response.getDietaryRestrictions());

        //update the ProfileViewModel state
        profileViewModel.setState(profileState);
        profileViewModel.firePropertyChanged();

        //switch to MealPlanView
        this.viewManagerModel.setState(meanPlanViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        // No need to add code here. We'll assume that profile can't fail.
        // Thought question: is this a reasonable assumption?
    }

    @Override
    public void switchToMealPlanView() {

    }
}

