package interface_adapter.healthyreminders;

import use_case.healthy_reminders.HealthyRemindersInputBoundary;

import java.io.IOException;

public class HealthyRemindersController {
    private final HealthyRemindersInputBoundary healthyRemindersInteractor;
    private HealthyRemindersViewModel healthyRemindersViewModel;

    public HealthyRemindersController(HealthyRemindersInputBoundary healthyRemindersInteractor) {
        this.healthyRemindersInteractor = healthyRemindersInteractor;
        this.healthyRemindersViewModel = healthyRemindersViewModel;
    }

//    public void setViewModel(HealthyRemindersViewModel healthyRemindersViewModel) {
//        this.healthyRemindersViewModel = healthyRemindersViewModel;
//    }

    public void generateReminder() throws IOException {
        String reminder = healthyRemindersInteractor.generateReminder();  // Calls the interactor to get the reminder
        healthyRemindersViewModel.setReminder(reminder);  //
//        healthyRemindersViewModel.getState().setCurrentReminder(reminder);
//        healthyRemindersViewModel.firePropertyChanged();
    }
}