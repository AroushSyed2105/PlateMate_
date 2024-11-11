package interface_adapter.meal_plan;

import interface_adapter.ViewModel;
import interface_adapter.profile.ProfileState;

public class MealPlanViewModel extends ViewModel<ProfileState> {

}
    public MealPlanViewModel() {
        super("Meal Plan");
        setState(new MealPlanState());
    }
