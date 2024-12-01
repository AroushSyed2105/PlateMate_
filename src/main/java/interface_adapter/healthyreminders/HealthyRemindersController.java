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
        System.out.println("Controller: Generating reminder...");
        String reminder = healthyRemindersInteractor.generateReminder();
        System.out.println("Controller: Received reminder: " + reminder);

        healthyRemindersViewModel.setReminder(reminder != null ? reminder : "No reminder generated.");
    }

//    public void generateReminder() throws IOException {
//        // Call the interactor's generateReminder() method
//        String reminder = healthyRemindersInteractor.generateReminder();
//
//        // Update the ViewModel with the fetched reminder
//        if (reminder != null) {
//            healthyRemindersViewModel.setReminder(reminder);
//        } else {
//            healthyRemindersViewModel.setReminder("Failed to generate a reminder. Please try again.");
//        }
//    }
}