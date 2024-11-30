package view;//package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.Box;

import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;


import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;

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
    private final JButton cancel;
    private final JButton toLogin;

    private Image backgroundImage; // Background image variable

    public SignupView(SignupViewModel signupViewModel) {
        try {
            backgroundImage = ImageIO.read(new File("images/background4.png")); // Replace with the path to your image
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

        // Define font for labels
        Font customFont = new Font("Times New Roman", Font.PLAIN, 16);


        this.signupViewModel = signupViewModel;
        signupViewModel.addPropertyChangeListener(this);

        // Set layout and make the background transparent
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createVerticalStrut(200));

        // Title
        final JLabel title = new JLabel(SignupViewModel.TITLE_LABEL);
        title.setFont(new Font("Times New Roman", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setOpaque(true);
        this.add(title);
        this.add(Box.createVerticalStrut(20));

        // Email Info
        JLabel emailLabel = new JLabel(signupViewModel.EMAIL_LABEL);
        emailLabel.setFont(customFont);
        emailInputField.setFont(customFont);
        final LabelTextPanel emailInfo = new LabelTextPanel(emailLabel, emailInputField);

        // Address Info
        JLabel addressLabel = new JLabel(SignupViewModel.ADDRESS_LABEL);
        addressLabel.setFont(customFont);
        addressInputField.setFont(customFont);
        final LabelTextPanel addressInfo = new LabelTextPanel(addressLabel, addressInputField);

        // Username Info
        JLabel usernameLabel = new JLabel(SignupViewModel.USERNAME_LABEL);
        usernameLabel.setFont(customFont);
        usernameInputField.setFont(customFont);
        final LabelTextPanel usernameInfo = new LabelTextPanel(usernameLabel, usernameInputField);

        // Password Info
        JLabel passwordLabel = new JLabel(SignupViewModel.PASSWORD_LABEL);
        passwordLabel.setFont(customFont);
        passwordInputField.setFont(customFont);
        final LabelTextPanel passwordInfo = new LabelTextPanel(passwordLabel, passwordInputField);

        // Repeat Password Info
        JLabel repeatPasswordLabel = new JLabel(SignupViewModel.REPEAT_PASSWORD_LABEL);
        repeatPasswordLabel.setFont(customFont);
        repeatPasswordInputField.setFont(customFont);
        final LabelTextPanel repeatPasswordInfo = new LabelTextPanel(repeatPasswordLabel, repeatPasswordInputField);

        // Buttons
        final JPanel buttons = new JPanel();

        toLogin = new JButton(SignupViewModel.TO_LOGIN_BUTTON_LABEL);
        toLogin.setFont(customFont);
        buttons.add(toLogin);

        signUp = new JButton(SignupViewModel.SIGNUP_BUTTON_LABEL);
        signUp.setFont(customFont);
        buttons.add(signUp);

        cancel = new JButton(SignupViewModel.CANCEL_BUTTON_LABEL);
        cancel.setFont(customFont);
        buttons.add(cancel);

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

        toLogin.addActionListener(evt -> signupController.switchToLoginView());
        cancel.addActionListener(this);

        // Add listeners
        addUsernameListener();
        addPasswordListener();
        addRepeatPasswordListener();
        addEmailListener();
        addAddressListener();

        title.setOpaque(false);

        this.add(usernameInfo);
        this.add(passwordInfo);
        this.add(repeatPasswordInfo);
        this.add(emailInfo);
        this.add(addressInfo);
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
        usernameInputField.setOpaque(false);
        passwordInputField.setOpaque(false);
        repeatPasswordInputField.setOpaque(false);
        emailInputField.setOpaque(false);
        addressInputField.setOpaque(false);

        // Ensure all panels are transparent
        for (Component c : this.getComponents()) {
            if (c instanceof JPanel) {
                ((JPanel) c).setOpaque(false);
            }
        }
    }


    // Document listeners for input fields (unchanged)
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

    // Action listener for cancel button (unchanged)
    @Override
    public void actionPerformed(ActionEvent e) {
        signupController.switchToLoginView();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Handle changes in the model state if needed
    }

    public String getViewName() {
        return viewName;
    }

    // Setter for SignupController
    public void setSignupController(SignupController signupController) {
        this.signupController = signupController;
    }
}

