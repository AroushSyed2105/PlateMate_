package interface_adapter.Calorie;

import use_case.meal_plan.MealPlanInputBoundary;

public class CalorieController {
    private final MealPlanInputBoundary mealPlanUseCaseInteractor;
    public CalorieController(MealPlanInputBoundary mealPlanUseCaseInteractor) {
        this.mealPlanUseCaseInteractor = mealPlanUseCaseInteractor;
    }
    public void switchToProfileView() { mealPlanUseCaseInteractor.switchToProfileView();}
}
