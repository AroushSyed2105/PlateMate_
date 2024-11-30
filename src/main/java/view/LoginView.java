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

import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;

/**
 * The View for when the user is logging into the program.
 */
public class LoginView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "log in";
    private final LoginViewModel loginViewModel;

    private final JTextField usernameInputField = new JTextField(15);
    private final JLabel usernameErrorField = new JLabel();

    private final JPasswordField passwordInputField = new JPasswordField(15);
    private final JLabel passwordErrorField = new JLabel();

    private final JButton logIn;
    private final JButton cancel;
    private LoginController loginController;
    private Image backgroundImage; // Background image variable

    public LoginView(LoginViewModel loginViewModel) {
        // Define custom font and background color

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
        Color customBackgroundColor = new Color(219, 232, 215);

        this.loginViewModel = loginViewModel;
        this.loginViewModel.addPropertyChangeListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createVerticalStrut(200));

        // Title label
        final JLabel title = new JLabel("Login Screen");
        title.setFont(new Font("Times New Roman", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setOpaque(true);
        title.setBackground(customBackgroundColor);
        //this.add(Box.createVerticalStrut(70));

        // Username field and label
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(customFont);
        usernameLabel.setOpaque(true);
        usernameLabel.setBackground(customBackgroundColor);
        usernameLabel.add(Box.createVerticalStrut(70));

        usernameInputField.setFont(customFont);
        usernameInputField.setBackground(new Color(238, 238, 238)); // Set to grey
        usernameInputField.setOpaque(true);

        LabelTextPanel usernameInfo = new LabelTextPanel(usernameLabel, usernameInputField);
        usernameInfo.setBackground(customBackgroundColor);

        // Password field and label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(customFont);
        passwordLabel.setOpaque(true);
        passwordLabel.setBackground(customBackgroundColor);

        passwordInputField.setFont(customFont);
        passwordInputField.setBackground(new Color(238, 238, 238)); // Set to grey
        passwordInputField.setOpaque(true);

        LabelTextPanel passwordInfo = new LabelTextPanel(passwordLabel, passwordInputField);
        passwordInfo.setBackground(customBackgroundColor);

        // Buttons
        final JPanel buttons = new JPanel();
        buttons.setBackground(customBackgroundColor);

        logIn = new JButton("Log In");
        logIn.setFont(customFont);
        buttons.add(logIn);

        cancel = new JButton("Cancel");
        cancel.setFont(customFont);
        buttons.add(cancel);

        // Add ActionListener for buttons
        logIn.addActionListener(evt -> {
            if (evt.getSource().equals(logIn)) {
                final LoginState currentState = loginViewModel.getState();

                loginController.execute(
                        currentState.getUsername(),
                        currentState.getPassword()
                );
            }
        });

        cancel.addActionListener(this);

        // Username input field document listener
        usernameInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final LoginState currentState = loginViewModel.getState();
                currentState.setUsername(usernameInputField.getText());
                loginViewModel.setState(currentState);
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

        // Password input field document listener
        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final LoginState currentState = loginViewModel.getState();
                currentState.setPassword(new String(passwordInputField.getPassword()));
                loginViewModel.setState(currentState);
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

        // Add components to the panel
        this.add(title);
        this.add(Box.createVerticalStrut(30));
        this.add(usernameInfo);
        this.add(usernameErrorField);
        this.add(passwordInfo);
        this.add(passwordErrorField);
        this.add(buttons);

        // Set error field fonts and background
        usernameErrorField.setFont(customFont);
        usernameErrorField.setBackground(customBackgroundColor);
        usernameErrorField.setOpaque(true);

        passwordErrorField.setFont(customFont);
        passwordErrorField.setBackground(customBackgroundColor);
        passwordErrorField.setOpaque(true);

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
      ;
        // Ensure all panels are transparent
        for (Component c : this.getComponents()) {
            if (c instanceof JPanel) {
                ((JPanel) c).setOpaque(false);
            }
        }
    }

    /**
     * React to a button click that results in evt.
     * @param evt the ActionEvent to react to
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final LoginState state = (LoginState) evt.getNewValue();
        setFields(state);
        usernameErrorField.setText(state.getLoginError());
    }

    private void setFields(LoginState state) {
        usernameInputField.setText(state.getUsername());
        passwordInputField.setText(state.getPassword());
    }

    public String getViewName() {
        return viewName;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }
}

