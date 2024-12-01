package interface_adapter.profile;

import use_case.user_profile.ProfileInputBoundary;
import use_case.user_profile.ProfileInputData;


public class ProfileController {
    private final ProfileInputBoundary profileUseCaseInteractor;

    public ProfileController(ProfileInputBoundary profileUseCaseInteractor) {
        this.profileUseCaseInteractor = profileUseCaseInteractor;
    }
    /**
     * Executes the Profile Use Case.
     * @param allergies is allgeries of the user
     * @param healthyGoals are the goals that the user wants us to follow in health plan
     * @param dietaryRestrictions are any eating restrictions they have that we should keep account for
     */
    public void execute(String[] allergies, String[] healthyGoals, String[] dietaryRestrictions, String username) {
        final ProfileInputData profileInputData = new ProfileInputData(allergies, healthyGoals, dietaryRestrictions, username);
        profileUseCaseInteractor.execute(profileInputData);
    }

    /**
     * Executes the "switch to MealView" Use Case.
     */
    public void switchToMealPlanView() {

        profileUseCaseInteractor.switchToMealPlanView();
    }

    public void switchtoCalorieView() {

        profileUseCaseInteractor.switchToCalorieView();
    }

    public void switchToGroceryView() {

        profileUseCaseInteractor.switchToGroceryView();
    }

    public void switchToLoggedInView() {
        profileUseCaseInteractor.switchToLoggedInView();
    }

}
