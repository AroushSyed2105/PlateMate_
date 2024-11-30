package use_case.calorie;

public class CalorieInteractor implements CalorieInputBoundary {
    private final CalorieOutputBoundary caloriePresenter;

    public CalorieInteractor(CalorieOutputBoundary caloriePresenter) {
        this.caloriePresenter = caloriePresenter;
    }
    public void switchToProfileView() { caloriePresenter.switchToProfileView(); }
    }

