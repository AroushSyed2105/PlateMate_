package interface_adapter.healthyreminders;

import interface_adapter.ViewManagerModel;
import interface_adapter.change_password.LoggedInViewModel;
import interface_adapter.meal_plan.MealPlanViewModel;
import interface_adapter.profile.ProfileViewModel;
import use_case.healthy_reminders.HealthyRemindersOutputBoundary;
import use_case.healthy_reminders.HealthyRemindersOutputData;

public class HealthyRemindersPresenter implements HealthyRemindersOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final LoggedInViewModel loggedInViewModel;
    private final HealthyRemindersViewModel healthyRemindersViewModel;
    private HealthyRemindersViewModel viewModel;
//
//    public HealthyRemindersPresenter(ViewManagerModel viewManagerModel) {
//        this.viewManagerModel = viewManagerModel;
//        this.viewModel = viewModel;
//    }

    public HealthyRemindersPresenter(ViewManagerModel viewManagerModel, LoggedInViewModel loggedInViewModel, HealthyRemindersViewModel healthyRemindersViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
        this.healthyRemindersViewModel = healthyRemindersViewModel;
    }

    @Override
    public void prepareSuccessView(HealthyRemindersOutputData response) {
        String reminder = response.getReminder();
        System.out.println("Presenter received reminder: " + reminder); // Fixed the variable name
        viewModel.setReminder(reminder); // Update the ViewModel
    }


    public void setViewModel(HealthyRemindersViewModel viewModel) {
        this.viewModel = viewModel;
    }
    @Override
    public void prepareFailView(String error) {
        viewModel.setReminder("Unable to generate a reminder. Please try again later.");
    }

    @Override
    public void switchToHealthyRemindersView() {
        // Notify listeners to switch to the Healthy Reminders view
        viewModel.firePropertyChanged();
    }
}
//package interface_adapter.healthyreminders;
//
//import interface_adapter.ViewManagerModel;
//import use_case.healthy_reminders.HealthyRemindersOutputBoundary;
//import use_case.healthy_reminders.HealthyRemindersOutputData;
//
//public class HealthyRemindersPresenter implements HealthyRemindersOutputBoundary {
//    private final HealthyRemindersViewModel healthyRemindersViewModel;
//    private final ViewManagerModel viewManagerModel;
//
//    // Constructor
//    public HealthyRemindersPresenter(ViewManagerModel viewManagerModel, HealthyRemindersViewModel healthyRemindersViewModel) {
//        this.viewManagerModel = viewManagerModel;
//        this.healthyRemindersViewModel = healthyRemindersViewModel;
//    }
//
//    @Override
//    public void prepareSuccessView(HealthyRemindersOutputData response) {
//        String reminder = response.getReminder();
//
//        // Update the view model
//        healthyRemindersViewModel.setReminder(reminder);
//
//        // Notify listeners (optional, if required)
//        healthyRemindersViewModel.firePropertyChanged();
//    }
//
//    @Override
//    public void prepareFailView(String error) {
//        // You can leave this empty or handle error messages if required
//    }
//
//    @Override
//    public void switchToHealthyRemindersView() {
//        // Notify the ViewManagerModel to switch to the "HealthyReminders" view
//        this.viewManagerModel.setState("HealthyReminders");
//        this.viewManagerModel.firePropertyChanged();
//    }
//}

//    @Override
//    public void prepareSuccessView(HealthyRemindersOutputData response) {
//        String reminder = response.getReminder();
//
//        // Show the reminder directly as a popup
//        JOptionPane.showMessageDialog(
//                null,
//                "Healthy Reminder: " + reminder,
//                "Daily Healthy Reminder",
//                JOptionPane.INFORMATION_MESSAGE
//        );
//    }