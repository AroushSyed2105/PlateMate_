package interface_adapter.Calorie;

import interface_adapter.ViewModel;

public class CalorieViewModel extends ViewModel<CalorieState> {

    public CalorieViewModel() {
        super("CalorieView");
        setState(new CalorieState());
    }
}
