package interface_adapter.groceries;

import use_case.grocery.GroceryInputBoundary;

public class GroceryController {
    private final GroceryInputBoundary groceryUseCaseInteractor;
    public GroceryController(GroceryInputBoundary groceryUseCaseInteractor) {
        this.groceryUseCaseInteractor = groceryUseCaseInteractor;
    }
    public void switchToProfileView() { groceryUseCaseInteractor.switchToProfileView();}
}
