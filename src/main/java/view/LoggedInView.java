package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import app.ChatPost;
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

    public LoggedInView(ProfileViewModel profileViewModel, LoggedInViewModel loggedInViewModel) {
        // Set the custom font and background color
        Font customFont = new Font("Times New Roman", Font.PLAIN, 16);
        Color customBackgroundColor = new Color(219, 232, 215);
        Color plainColour = new Color(238, 238, 238);

    public LoggedInView(ProfileViewModel profileViewModel, LoggedInViewModel loggedInViewModel, HealthyRemindersViewModel healthyRemindersViewModel1) {
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
        JLabel usernameInfo = new JLabel("Currently logged in: ");
        usernameInfo.setFont(customFont);
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
        buttons.add(toHealthyReminders);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

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
        toProfile.addActionListener(evt -> loggedInController.switchToProfileView());
        /// copy this
        toProfile.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        loggedInController.switchToProfileView();
                    }
                }
        );

        changePassword.addActionListener(evt -> {
            if (evt.getSource().equals(changePassword)) {
                LoggedInState currentState = loggedInViewModel.getState();
                changePasswordController.execute(currentState.getUsername(), currentState.getPassword());
            }
        });
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
        this.add(usernameInfo);
        this.add(username);
        this.add(passwordInfo);
        this.add(passwordErrorField);
        this.add(buttons);
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
//   public void switchToHealthyRemindersView() {
//        viewManagerModel.setState("HealthyReminders");
//        viewManagerModel.firePropertyChanged();
//    }
}

