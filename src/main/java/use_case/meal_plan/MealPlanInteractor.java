package use_case.meal_plan;

public class MealPlanInteractor implements MealPlanInputBoundary {
    private final MealPlanOutputBoundary mealPlanPresenter;

    public MealPlanInteractor(MealPlanOutputBoundary mealPlanPresenter) {
        this.mealPlanPresenter = mealPlanPresenter;
    }
    public void switchToProfileView() { mealPlanPresenter.switchToProfileView(); }

    @Override
    public void switchToNotesView() {
        mealPlanPresenter.switchToNotesView();
    }
}

