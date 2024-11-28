package interface_adapter.meal_plan;

import interface_adapter.ViewManagerModel;
import interface_adapter.profile.ProfileViewModel;
import use_case.user_profile.ProfileOutputBoundary;
import use_case.user_profile.ProfileOutputData;

/**
 * The Presenter for the Login Use Case.
 */
public class MealPlanPresenter implements MealPlanOutputBoundary {
    private final ProfileViewModel profileViewModel;
    private final MealPlanViewModel mealPlanViewModel;
    private final ViewManagerModel viewManagerModel;

    public MealPlanPresenter(ViewManagerModel viewManagerModel,
                            MealPlanViewModel mealPlanViewModel,
                            ProfileViewModel profileViewModel
    ) {
        this.profileViewModel = profileViewModel;
        this.viewManagerModel = viewManagerModel;
        this.mealPlanViewModel = mealPlanViewModel;
    }

    @Override
    public void prepareSuccessView(ProfileOutputData response) {
        // On success, switch to the meal plan view.
        final MealPlanState mealPlanState = mealPlanViewModel.getState();
        //update the MealPlan state
        profileViewModel.setState(mealPlanState);
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
        this.viewManagerModel.setState(profileViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }
}
