package use_case.calorie;

import use_case.meal_plan.MealPlanInputBoundary;
import use_case.meal_plan.MealPlanOutputBoundary;

public class CalorieInteractor implements MealPlanInputBoundary {
    private final MealPlanOutputBoundary mealPlanPresenter;

    public CalorieInteractor(MealPlanOutputBoundary mealPlanPresenter) {
        this.mealPlanPresenter = mealPlanPresenter;
    }
    public void switchToProfileView() { mealPlanPresenter.switchToProfileView(); }
    }

