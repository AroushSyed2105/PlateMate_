package use_case.user_profile;


public interface ProfileInputBoundary {

    /**
     * Executes the profile use case.
     * @param profileInputData the input data
     */
    void execute(ProfileInputData profileInputData);

    void switchToMealPlanView();

    void switchToCalorieView();

    void switchToGroceryView();
}