package view;

import interface_adapter.healthyreminders.HealthyRemindersController;
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
                        "No reminder available. Please try generating a reminder again.",
                        "No Reminder",
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
}
