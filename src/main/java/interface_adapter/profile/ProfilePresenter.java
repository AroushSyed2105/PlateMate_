package interface_adapter.profile;

import interface_adapter.ViewManagerModel;
import interface_adapter.change_password.LoggedInViewModel;
import interface_adapter.meal_plan.MealPlanViewModel;
import interface_adapter.groceries.GroceryViewModel;
import use_case.user_profile.ProfileOutputBoundary;
import use_case.user_profile.ProfileOutputData;

/**
 * The Presenter for the Login Use Case.
 */
public class ProfilePresenter implements ProfileOutputBoundary {
    private final ProfileViewModel profileViewModel;
    private final LoggedInViewModel loggedInViewModel;
    private final MealPlanViewModel mealPlanViewModel;
    private final ViewManagerModel viewManagerModel;
    private final GroceryViewModel groceryViewModel;

    public ProfilePresenter(ViewManagerModel viewManagerModel, LoggedInViewModel loggedInViewModel,
                            MealPlanViewModel mealPlanViewModel,
                            ProfileViewModel profileViewModel, GroceryViewModel groceryViewModel
                            ) {
        this.profileViewModel = profileViewModel;
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
        this.mealPlanViewModel = mealPlanViewModel;
        this.groceryViewModel = groceryViewModel;
    }

    @Override
    public void prepareSuccessView(ProfileOutputData response) {
        // On success, switch to the meal plan view.
        final ProfileState profileState = profileViewModel.getState();

        //Update the profile information
        profileState.setUsername(response.getUsername());
        profileState.setAllergies(response.getAllergies());
        profileState.setHealthGoals(response.getHealthGoals());
        profileState.setDietaryRestrictions(response.getDietaryRestrictions());
        //update the ProfileViewModel state
        profileViewModel.setState(profileState);
        profileViewModel.firePropertyChanged();
        //switch to MealPlanView
        this.viewManagerModel.setState(mealPlanViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        // No need to add code here. We'll assume that profile can't fail.
        // Thought question: is this a reasonable assumption?
    }

    @Override
    public void switchToMealPlanView() {
        this.viewManagerModel.setState(mealPlanViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    public void switchToGroceryView() {
        this.viewManagerModel.setState(groceryViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    public void switchToLoggedInView() {
        this.viewManagerModel.setState(loggedInViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }
}

