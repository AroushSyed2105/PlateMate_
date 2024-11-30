package interface_adapter.logged_in;

import interface_adapter.healthyreminders.HealthyRemindersController;
import use_case.logged_in.LoggedInInputBoundary;

public class LoggedInController {

    private final LoggedInInputBoundary loggedInUseCaseInteractor;

    private HealthyRemindersController healthyRemindersController = null;

    public LoggedInController(LoggedInInputBoundary loggedInUseCaseInteractor) {
        this.loggedInUseCaseInteractor = loggedInUseCaseInteractor;
    }


    public void switchToProfileView() {
        loggedInUseCaseInteractor.switchToProfileView();
    }

    public void switchToHealthyRemindersView() { loggedInUseCaseInteractor.switchToHealthyRemindersView();}

    public void setHealthyRemindersController(HealthyRemindersController healthyRemindersController) {
        this.healthyRemindersController = healthyRemindersController;
    }
}