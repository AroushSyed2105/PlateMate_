package view;

import interface_adapter.healthyreminders.HealthyRemindersController;
import interface_adapter.healthyreminders.HealthyRemindersState;
import interface_adapter.healthyreminders.HealthyRemindersViewModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class HealthyRemindersView extends JPanel {
    private final String viewName = "HealthyReminders";
    private transient HealthyRemindersController healthyRemindersController;

    private Image backgroundImage;

    public HealthyRemindersView(HealthyRemindersViewModel healthyRemindersViewModel) {
        try {
            backgroundImage = ImageIO.read(new File("images/BG4.png")); // Replace with the path to your image
            if (backgroundImage == null) {
                System.out.println("Error: Image not found.");
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle exception if image is not found
        }


        // Set layout and make the background transparent
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Ensure the panel is transparent
        this.setOpaque(false);


        JLabel titleLabel = new JLabel("Daily Healthy Reminders");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton generateReminderButton = new JButton("Generate Reminder");
        generateReminderButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createVerticalStrut(300));

        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(evt-> healthyRemindersController.switchToLoggedInView());

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
        this.add(Box.createVerticalStrut(30));
        this.add(generateReminderButton);
        this.add(Box.createVerticalStrut(20));
        this.add(backButton);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            // Draw the background image to cover the entire panel
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setHealthyRemindersController(HealthyRemindersController healthyRemindersController) {
        this.healthyRemindersController = healthyRemindersController;
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
