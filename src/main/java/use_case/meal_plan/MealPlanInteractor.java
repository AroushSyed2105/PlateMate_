package use_case.meal_plan;

import entity.Profile;
import entity.ProfileFactory;
import use_case.user_profile.ProfileInputData;
import use_case.user_profile.ProfileOutputBoundary;
import use_case.user_profile.ProfileOutputData;
import use_case.user_profile.ProfileUserDataAccessInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MealPlanInteractor  implements  MealPlanInputBoundary{
    private final MealPlanDataAccessInterface mealPlanDataAccessObject;
    private final MealPlanOutputBoundary mealPlanPresenter;

    public MealPlanInteractor(MealPlanDataAccessInterface mealPlanDataAccessInterface,
                             MealPlanOutputBoundary mealPlanOutputBoundary) {
        this.mealPlanDataAccessObject = mealPlanDataAccessInterface;
        this.mealPlanPresenter = mealPlanOutputBoundary;
    }

    @Override
    public void switchToCalorieTrackerView() {
    }

    @Override
    public void execute(MealPlanInputData mealPlanInputData) {
        // Fetch data from the data access object
        Map<String, String> mealPlan = mealPlanDataAccessObject.getMealPlan(mealPlanInputData);

        // Use the presenter to format data for the view
        mealPlanPresenter.presentMealPlan(mealPlan);
    }


}
