package view;

import interface_adapter.healthyreminders.HealthyRemindersController;
import interface_adapter.healthyreminders.HealthyRemindersState;
import interface_adapter.healthyreminders.HealthyRemindersViewModel;

import javax.swing.*;
import java.awt.*;

public class HealthyRemindersView extends JPanel {
    private final String viewName = "HealthyReminders";

    public HealthyRemindersView(HealthyRemindersViewModel healthyRemindersViewModel) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Daily Healthy Reminders");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton generateReminderButton = new JButton("Generate Reminder");
        generateReminderButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Button click shows the reminder stored in the ViewModel
        generateReminderButton.addActionListener(e -> {
            String reminder = healthyRemindersViewModel.getState().getCurrentReminder();
            System.out.println("Reminder fetched in View: " + reminder); // Add this line
            if (reminder != null && !reminder.isEmpty()) {
                JOptionPane.showMessageDialog(
                        null,
                        "Healthy Reminder: " + reminder,
                        "Daily Healthy Reminder",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Stay hydrated! Aim for about 8 glasses (2 liters) of water a day, or more if you're active. Take a sip now!.",
                        "Daily Healthy Reminder",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        });

        this.add(titleLabel);
        this.add(generateReminderButton);
    }

    public String getViewName() {
        return viewName;
    }

//    public void setReminder(String reminder) {
//        HealthyRemindersState state = getState(); // Access the current state
//        state.setCurrentReminder(reminder);      // Update the reminder
//        firePropertyChanged();                   // Notify listeners
//    }

    private void firePropertyChanged() {
        // Notify observers/listeners about the state change.
        // This could be done using PropertyChangeSupport or another mechanism.
        System.out.println("Property changed!");
    }
}
