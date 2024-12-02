package interface_adapter.notes;

import interface_adapter.ViewManagerModel;
import interface_adapter.meal_plan.MealPlanViewModel;
import interface_adapter.profile.ProfileViewModel;
import use_case.notes.NotesOutputBoundary;

/**
 * The Presenter for the Login Use Case.
 */
public class NotesPresenter implements NotesOutputBoundary {
    private final MealPlanViewModel mealPlanViewModel;
    private final ViewManagerModel viewManagerModel;

    public NotesPresenter(ViewManagerModel viewManagerModel,
                          MealPlanViewModel mealPlanViewModel
    ) {
        this.mealPlanViewModel = mealPlanViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void switchToMealPlanView() {
        this.viewManagerModel.setState(mealPlanViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }
}
