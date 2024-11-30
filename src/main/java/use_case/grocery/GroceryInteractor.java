package use_case.grocery;

public class GroceryInteractor implements GroceryInputBoundary {
    private final GroceryOutputBoundary groceryPresenter;

    public GroceryInteractor(GroceryOutputBoundary groceryPresenter) {
        this.groceryPresenter = groceryPresenter;
    }

    public void switchToProfileView() { groceryPresenter.switchToProfileView(); }
    }

