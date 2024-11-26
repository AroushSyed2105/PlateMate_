package use_case.meal_plan;

import use_case.user_profile.ProfileInputData;

public class MealPlanInputBoundary {

    /**
     * Executes the profile use case.
     * @param MealPlanInputData the input data
     */
    void execute(MealPlanInputData mealPLanInputData);

    void switchToCalorieTracker();
}