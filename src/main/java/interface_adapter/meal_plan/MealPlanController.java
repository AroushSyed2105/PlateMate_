package interface_adapter.meal_plan;

import use_case.meal_plan.MealPlanInputBoundary;

public class MealPlanController {
    private final MealPlanInputBoundary mealPlanUseCaseInteractor;
    public MealPlanController(MealPlanInputBoundary mealPlanUseCaseInteractor) {
        this.mealPlanUseCaseInteractor = mealPlanUseCaseInteractor;
    }
    public void switchToProfileView() { mealPlanUseCaseInteractor.switchToProfileView();}

    public void switchToNotesView() { mealPlanUseCaseInteractor.switchToNotesView();
    }
}
