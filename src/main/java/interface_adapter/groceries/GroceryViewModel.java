package interface_adapter.groceries;
import interface_adapter.ViewModel;
import interface_adapter.meal_plan.MealPlanState;

public class GroceryViewModel extends ViewModel<GroceryState>{

    public GroceryViewModel() {
        super("Grocery");
        setState(new GroceryState());
    }
}
