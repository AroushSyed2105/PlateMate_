package use_case.grocery;

import use_case.meal_plan.MealPlanInputBoundary;
import use_case.meal_plan.MealPlanOutputBoundary;

public class GroceryInteractor implements MealPlanInputBoundary {
    private final MealPlanOutputBoundary mealPlanPresenter;

    public GroceryInteractor(MealPlanOutputBoundary mealPlanPresenter) {
        this.mealPlanPresenter = mealPlanPresenter;
    }
    public void switchToProfileView() { mealPlanPresenter.switchToProfileView(); }
    }

