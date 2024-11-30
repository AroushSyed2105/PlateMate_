package interface_adapter.Calorie;

import use_case.calorie.CalorieInputBoundary;

public class CalorieController {
    private final CalorieInputBoundary CalorieUseCaseInteractor;
    public CalorieController(CalorieInputBoundary calorieUseCaseInteractor) {
        this.CalorieUseCaseInteractor = calorieUseCaseInteractor;
    }
    public void switchToProfileView() { CalorieUseCaseInteractor.switchToProfileView();}
}
