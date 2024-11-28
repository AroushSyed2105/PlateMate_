package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;

/**
 * The View for the Signup Use Case.
 */
public class SignupView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "sign up";

    private final SignupViewModel signupViewModel;
    private final JTextField usernameInputField = new JTextField(15);
    private final JPasswordField passwordInputField = new JPasswordField(15);
    private final JPasswordField repeatPasswordInputField = new JPasswordField(15);
    private final JTextField emailInputField = new JTextField(15);
    private final JTextField addressInputField = new JTextField(15);
    private SignupController signupController;

    private final JButton signUp;
    private final JButton shutDown;
    private final JButton toLogin;

    public SignupView(SignupViewModel signupViewModel) {
        // Define font and background color
        Font customFont = new Font("Times New Roman", Font.PLAIN, 16);
        Color customBackgroundColor = new Color(219, 232, 215);
        Color inputFieldColor = new Color(238, 238, 238); // Light grey color

        this.signupViewModel = signupViewModel;
        signupViewModel.addPropertyChangeListener(this);

        // Set layout and background color for the main panel
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(customBackgroundColor);

        // Title
        final JLabel title = new JLabel(SignupViewModel.TITLE_LABEL);
        title.setFont(new Font("Times New Roman", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setOpaque(true);
        title.setBackground(customBackgroundColor);

        // Email Info
        JLabel emailLabel = new JLabel(signupViewModel.EMAIL_LABEL);
        emailLabel.setFont(customFont);
        emailInputField.setFont(customFont);
        emailInputField.setBackground(inputFieldColor);
        emailInputField.setOpaque(true);
        final LabelTextPanel emailInfo = new LabelTextPanel(emailLabel, emailInputField);
        emailInfo.setBackground(customBackgroundColor);

        // Address Info
        JLabel addressLabel = new JLabel(SignupViewModel.ADDRESS_LABEL);
        addressLabel.setFont(customFont);
        addressInputField.setFont(customFont);
        addressInputField.setBackground(inputFieldColor);
        addressInputField.setOpaque(true);
        final LabelTextPanel addressInfo = new LabelTextPanel(addressLabel, addressInputField);
        addressInfo.setBackground(customBackgroundColor);

        // Username Info
        JLabel usernameLabel = new JLabel(SignupViewModel.USERNAME_LABEL);
        usernameLabel.setFont(customFont);
        usernameInputField.setFont(customFont);
        usernameInputField.setBackground(inputFieldColor);
        usernameInputField.setOpaque(true);
        final LabelTextPanel usernameInfo = new LabelTextPanel(usernameLabel, usernameInputField);
        usernameInfo.setBackground(customBackgroundColor);

        // Password Info
        JLabel passwordLabel = new JLabel(SignupViewModel.PASSWORD_LABEL);
        passwordLabel.setFont(customFont);
        passwordInputField.setFont(customFont);
        passwordInputField.setBackground(inputFieldColor);
        passwordInputField.setOpaque(true);
        final LabelTextPanel passwordInfo = new LabelTextPanel(passwordLabel, passwordInputField);
        passwordInfo.setBackground(customBackgroundColor);

        // Repeat Password Info
        JLabel repeatPasswordLabel = new JLabel(SignupViewModel.REPEAT_PASSWORD_LABEL);
        repeatPasswordLabel.setFont(customFont);
        repeatPasswordInputField.setFont(customFont);
        repeatPasswordInputField.setBackground(inputFieldColor);
        repeatPasswordInputField.setOpaque(true);
        final LabelTextPanel repeatPasswordInfo = new LabelTextPanel(repeatPasswordLabel, repeatPasswordInputField);
        repeatPasswordInfo.setBackground(customBackgroundColor);

        // Buttons
        final JPanel buttons = new JPanel();
        buttons.setBackground(customBackgroundColor);

        toLogin = new JButton(SignupViewModel.TO_LOGIN_BUTTON_LABEL);
        toLogin.setFont(customFont);
        buttons.add(toLogin);

        signUp = new JButton(SignupViewModel.SIGNUP_BUTTON_LABEL);
        signUp.setFont(customFont);
        buttons.add(signUp);
        shutDown = new JButton(SignupViewModel.EXIT_BUTTON_LABEL);
        shutDown.setFont(customFont);
        buttons.add(shutDown);

        signUp.addActionListener(evt -> {
            if (evt.getSource().equals(signUp)) {
                final SignupState currentState = signupViewModel.getState();

                signupController.execute(
                        currentState.getUsername(),
                        currentState.getPassword(),
                        currentState.getRepeatPassword(),
                        currentState.getEmail(),
                        currentState.getAddress()
                );
            }
        });

        toLogin.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        signupController.switchToLoginView();
                    }
                }
        );

        shutDown.addActionListener(evt -> System.exit(0));

        // Add listeners
        addUsernameListener();
        addPasswordListener();
        addRepeatPasswordListener();
        addEmailListener();
        addAddressListener();

        // Add components to the panel
        this.add(title);
        this.add(usernameInfo);
        this.add(passwordInfo);
        this.add(repeatPasswordInfo);
        this.add(emailInfo);
        this.add(addressInfo);
        this.add(buttons);
    }

    private void addEmailListener() {
        emailInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setEmail(emailInputField.getText());
                signupViewModel.setState(currentState);
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
    }

    private void addAddressListener() {
        addressInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setAddress(addressInputField.getText());
                signupViewModel.setState(currentState);
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
    }

    private void addUsernameListener() {
        usernameInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setUsername(usernameInputField.getText());
                signupViewModel.setState(currentState);
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
    }

    private void addPasswordListener() {
        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setPassword(new String(passwordInputField.getPassword()));
                signupViewModel.setState(currentState);
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
    }

    private void addRepeatPasswordListener() {
        repeatPasswordInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setRepeatPassword(new String(repeatPasswordInputField.getPassword()));
                signupViewModel.setState(currentState);
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
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "Cancel not implemented yet.");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final SignupState state = (SignupState) evt.getNewValue();
        if (state.getUsernameError() != null) {
            JOptionPane.showMessageDialog(this, state.getUsernameError());
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setSignupController(SignupController controller) {
        this.signupController = controller;
    }
}

