package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
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

    public LoggedInView(ProfileViewModel profileViewModel, LoggedInViewModel loggedInViewModel, HealthyRemindersViewModel healthyRemindersViewModel1) {
        this.profileViewModel = profileViewModel;
        this.loggedInViewModel = loggedInViewModel;
        this.healthyRemindersViewModel = healthyRemindersViewModel1;
        this.loggedInViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("Logged In Screen");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final LabelTextPanel passwordInfo = new LabelTextPanel(
                new JLabel("Password"), passwordInputField);

        final JLabel usernameInfo = new JLabel("Currently logged in: ");
        username = new JLabel();

        final JPanel buttons = new JPanel();
        logOut = new JButton("Log Out");
        buttons.add(logOut);

        changePassword = new JButton("Change Password");
        buttons.add(changePassword);

        toProfile = new JButton("Go to Profile");
        buttons.add(toProfile);

        toHealthyReminders = new JButton("Go to Daily Healthy Reminders");
        buttons.add(toHealthyReminders);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final LoggedInState currentState = loggedInViewModel.getState();
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

        /// copy this
        toProfile.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        loggedInController.switchToProfileView();
                    }
                }
        );

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

        logOut.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                evt -> {
                    if (evt.getSource().equals(logOut)) {
                        final LoggedInState currentState = loggedInViewModel.getState();
                        final String currentUsername = currentState.getUsername();
                        // 1. get the state out of the loggedInViewModel. It contains the username.
                        // 2. Execute the logout Controller.
                        logoutController.execute(currentUsername);
                    }
                }
        );

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
            final LoggedInState state = (LoggedInState) evt.getNewValue();
            username.setText(state.getUsername());
        }
        else if (evt.getPropertyName().equals("password")) {
            final LoggedInState state = (LoggedInState) evt.getNewValue();
            JOptionPane.showMessageDialog(null, "password updated for " + state.getUsername());
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
}
