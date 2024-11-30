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

//public class HealthyRemindersController {
//    private final HealthyRemindersInputBoundary interactor;
//    private HealthyRemindersViewModel viewModel;
//
//    public HealthyRemindersController(HealthyRemindersInputBoundary interactor) {
//        this.interactor = interactor;
//    }
//    public void setViewModel(HealthyRemindersViewModel viewModel) {
//        this.viewModel = viewModel;
//    }
//
//
//    public void generateReminder() {
//        interactor.generateReminder();
//    }
//}


//package interface_adapter.healthyreminders;
//
//import use_case.healthy_reminders.HealthyRemindersInputBoundary;
//import interface_adapter.healthyreminders.HealthyRemindersPresenter;
//
//import javax.swing.*;
//
//public class HealthyRemindersController {
//    private final HealthyRemindersInputBoundary healthyRemindersInteractor;
//
//    public HealthyRemindersController(HealthyRemindersInputBoundary healthyRemindersInteractor) {
//        this.healthyRemindersInteractor = healthyRemindersInteractor;
//    }
//
//    /**
//     * Executes the Healthy Reminders Use Case to generate a reminder.
//     * @return A randomly generated healthy reminder.
//     */
////    public String generateReminder() {
////        return healthyRemindersInteractor.generateReminder();
////    }
//    public void generateReminder() {
//        try {
//            String reminder = healthyRemindersInteractor.generateReminder();
//
//            if (reminder == null || reminder.isEmpty()) {
//                throw new Exception("No Healthy Daily Reminder is generated.");
//            }
//
//            // Display the reminder to the user
//            JOptionPane.showMessageDialog(
//                    null,
//                    "Healthy Reminder: \n" + reminder,
//                    "Daily Healthy Reminder",
//                    JOptionPane.INFORMATION_MESSAGE
//            );
//        } catch (Exception e) {
//            // Handle errors by showing an error message
//            JOptionPane.showMessageDialog(
//                    null,
//                    "Failed to generate a healthy reminder. Please try again later.",
//                    "Error",
//                    JOptionPane.ERROR_MESSAGE
//            );
//        }
//    }
//
//}