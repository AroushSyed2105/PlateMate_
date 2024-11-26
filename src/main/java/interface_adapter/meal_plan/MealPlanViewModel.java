package interface_adapter.meal_plan;

import interface_adapter.ViewModel;

public class MealPlanViewModel extends ViewModel<MealPlanState> {

    public MealPlanViewModel() {
        super("MealPlan");
        setState(new MealPlanState());
    }

}

