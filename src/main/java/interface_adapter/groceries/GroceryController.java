package interface_adapter.groceries;

import use_case.meal_plan.MealPlanInputBoundary;

public class GroceryController {
    private final MealPlanInputBoundary mealPlanUseCaseInteractor;
    public GroceryController(MealPlanInputBoundary mealPlanUseCaseInteractor) {
        this.mealPlanUseCaseInteractor = mealPlanUseCaseInteractor;
    }
    public void switchToProfileView() { mealPlanUseCaseInteractor.switchToProfileView();}
}
