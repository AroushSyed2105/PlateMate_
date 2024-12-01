package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

//import app.ChatPost;
import interface_adapter.change_password.ChangePasswordController;
import interface_adapter.change_password.LoggedInState;
import interface_adapter.change_password.LoggedInViewModel;
import interface_adapter.healthyreminders.HealthyRemindersController;
import interface_adapter.healthyreminders.HealthyRemindersViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.profile.ProfileController;
import interface_adapter.logged_in.LoggedInController;
import interface_adapter.profile.ProfileViewModel;

/**
 * The View for when the user is logged into the program.
 */
public class LoggedInView extends JPanel implements PropertyChangeListener {

    private final String viewName = "logged in";
    private final ProfileViewModel profileViewModel;
    private final LoggedInViewModel loggedInViewModel;
    private final HealthyRemindersViewModel healthyRemindersViewModel;
    private final JLabel passwordErrorField = new JLabel();
    private ChangePasswordController changePasswordController;
    private LogoutController logoutController;
    private ProfileController profileController;
    private LoggedInController loggedInController;

    private final JLabel username;
    private final JButton logOut;
    private final JButton toProfile;

    private final JButton toHealthyReminders;


    private final JTextField passwordInputField = new JTextField(15);
    private final JButton changePassword;
    private HealthyRemindersController healthyRemindersController;
    private Image backgroundImage; // Background image variable


    public LoggedInView(ProfileViewModel profileViewModel, LoggedInViewModel loggedInViewModel, HealthyRemindersViewModel healthyRemindersViewModel1) {
        // Set the custom font and background color
        try {
            backgroundImage = ImageIO.read(new File("images/BG3.png")); // Replace with the path to your image
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

        Font customFont = new Font("Times New Roman", Font.PLAIN, 16);
        //Color customBackgroundColor = new Color(219, 232, 215);
        Color customBackgroundColor = new Color(219, 232, 215);
        Color plainColour = new Color(238, 238, 238);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createVerticalStrut(200));

        this.profileViewModel = profileViewModel;
        this.loggedInViewModel = loggedInViewModel;
        this.healthyRemindersViewModel = healthyRemindersViewModel1;
        this.loggedInViewModel.addPropertyChangeListener(this);

        // Set the layout and background color
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(customBackgroundColor);

        // Title
        JLabel title = new JLabel("Logged In Screen");
        title.setFont(new Font("Times New Roman", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setOpaque(true);
        title.setBackground(customBackgroundColor);
        this.add(Box.createVerticalStrut(20));

        // Password Info
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(customFont);
        passwordLabel.setOpaque(true); // Allow background color to show
        passwordLabel.setBackground(customBackgroundColor); // Set background to green

        passwordInputField.setFont(customFont);
        passwordInputField.setOpaque(true); // Allow background color to show
        passwordInputField.setBackground(plainColour); // Set background to green


        LabelTextPanel passwordInfo = new LabelTextPanel(passwordLabel, passwordInputField);
        passwordInfo.setBackground(customBackgroundColor); // Ensure panel background is green
        passwordInfo.setOpaque(true); // Allow the background to show

        // Username Info
        JLabel usernameInfo = new JLabel("Currently Logged in: ");
        usernameInfo.setFont(customFont);
        usernameInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameInfo.setOpaque(true);
        usernameInfo.setBackground(customBackgroundColor);

        username = new JLabel();
        username.setFont(customFont);
        username.setOpaque(true);
        username.setBackground(customBackgroundColor);

        // Buttons Panel
        JPanel buttons = new JPanel();
        buttons.setBackground(customBackgroundColor);

        logOut = new JButton("Log Out");
        logOut.setFont(customFont);
        buttons.add(logOut);

        changePassword = new JButton("Change Password");
        changePassword.setFont(customFont);
        buttons.add(changePassword);

        toProfile = new JButton("Go to Profile");
        toProfile.setFont(customFont);
        buttons.add(toProfile);

        toHealthyReminders = new JButton("Go to Daily Healthy Reminders");
        toHealthyReminders.setFont(customFont);
        buttons.add(toHealthyReminders);

        // Add DocumentListener for password input
        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                LoggedInState currentState = loggedInViewModel.getState();
                currentState.setPassword(passwordInputField.getText());
                loggedInViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });

        // Add ActionListeners for buttons

        toProfile.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        loggedInController.switchToProfileView();
                    }
                }
        );
//        toHealthyReminders.addActionListener(evt -> {
//            try {
//                healthyRemindersController.generateReminder();  // Trigger reminder generation
//
//                // Fetch and display the reminder
//                String reminder = healthyRemindersViewModel.getState().getCurrentReminder();
//                System.out.println("Reminder fetched in View: " + reminder);
//
//                JOptionPane.showMessageDialog(
//                        null,
//                        reminder != null && !reminder.isEmpty() ? "Healthy Reminder: " + reminder : "No reminder available. Please try generating a reminder again.",
//                        "Daily Healthy Reminder",
//                        JOptionPane.INFORMATION_MESSAGE
//                );
//            } catch (IOException ex) {
//                JOptionPane.showMessageDialog(
//                        null,
//                        "Failed to generate a reminder. Please try again later.",
//                        "Error",
//                        JOptionPane.ERROR_MESSAGE
//                );
//                ex.printStackTrace();
//            }
//        });

        toHealthyReminders.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        loggedInController.switchToHealthyRemindersView();
                    }
                }
        );


        changePassword.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                evt -> {
                    if (evt.getSource().equals(changePassword)) {
                        final LoggedInState currentState = loggedInViewModel.getState();

                        this.changePasswordController.execute(
                                currentState.getUsername(),
                                currentState.getPassword()
                        );
                    }
                }
        );

        logOut.addActionListener(evt -> {
            if (evt.getSource().equals(logOut)) {
                LoggedInState currentState = loggedInViewModel.getState();
                String currentUsername = currentState.getUsername();
                logoutController.execute(currentUsername);
            }
        });

        // Add components to the panel
        this.add(title);
        this.add(Box.createVerticalStrut(30));
        this.add(usernameInfo);
        this.add(Box.createVerticalStrut(30));
        this.add(username);
        this.add(passwordInfo);
        this.add(passwordErrorField);
        this.add(buttons);

        this.add(Box.createVerticalGlue());
        setComponentTransparency();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            // Draw the background image to cover the entire panel
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    // Add this in the constructor, after all components are initialized:
    private void setComponentTransparency() {
        passwordInputField.setOpaque(false);
        ;
        // Ensure all panels are transparent
        for (Component c : this.getComponents()) {
            if (c instanceof JPanel) {
                ((JPanel) c).setOpaque(false);
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            LoggedInState state = (LoggedInState) evt.getNewValue();
            username.setText(state.getUsername());
        } else if (evt.getPropertyName().equals("password")) {
            LoggedInState state = (LoggedInState) evt.getNewValue();
            JOptionPane.showMessageDialog(null, "Password updated for " + state.getUsername());
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setChangePasswordController(ChangePasswordController changePasswordController) {
        this.changePasswordController = changePasswordController;
    }

    public void setProfileController(ProfileController profileController) {
        this.profileController = profileController;
    }

    public void setLogoutController(LogoutController logoutController) {
        this.logoutController = logoutController;
    }

    public void setLoggedInController(LoggedInController controller) {
        this.loggedInController = controller;
    }

    public void setHealthyRemindersController(HealthyRemindersController healthyRemindersController) {
        this.healthyRemindersController = healthyRemindersController;
    }

}


