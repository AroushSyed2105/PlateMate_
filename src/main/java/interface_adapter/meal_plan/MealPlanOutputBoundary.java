package interface_adapter.meal_plan;

import use_case.user_profile.ProfileOutputData;

public interface MealPlanOutputBoundary {
    /**
     * Prepares the success view for the Profile Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(ProfileOutputData outputData);

    /**
     * Prepares the failure view for the Profile Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);

    void switchToMealPlanView();
}
