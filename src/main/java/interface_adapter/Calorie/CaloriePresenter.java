package interface_adapter.Calorie;

import interface_adapter.ViewManagerModel;
import interface_adapter.profile.ProfileViewModel;
import use_case.meal_plan.MealPlanOutputBoundary;
import use_case.user_profile.ProfileOutputData;

/**
 * The Presenter for the Login Use Case.
 */
public class CaloriePresenter implements MealPlanOutputBoundary {
    private final ProfileViewModel profileViewModel;
    private final ViewManagerModel viewManagerModel;

    public CaloriePresenter(ViewManagerModel viewManagerModel,
                            ProfileViewModel profileViewModel
    ) {
        this.profileViewModel = profileViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(ProfileOutputData outputData) {
        // Taken care of by MealPlanView.
    }

    @Override
    public void prepareFailView(String error) {
        // Assumes view cannot fail.
    }

    @Override
    public void switchToProfileView() {
        this.viewManagerModel.setState(profileViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

}
