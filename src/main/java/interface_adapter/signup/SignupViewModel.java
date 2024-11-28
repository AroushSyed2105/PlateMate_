package interface_adapter.signup;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the Signup View.
 */
public class SignupViewModel extends ViewModel<SignupState> {

    public static final String TITLE_LABEL = "Welcome to PlateMate!";
    public static final String USERNAME_LABEL = "Choose username";
    public static final String PASSWORD_LABEL = "Choose password";
    public static final String REPEAT_PASSWORD_LABEL = "Enter password again";
    public static final String EMAIL_LABEL = "Enter email";
    public static final String ADDRESS_LABEL = "Enter address";
    public static final String SIGNUP_BUTTON_LABEL = "Sign up";
    public static final String EXIT_BUTTON_LABEL = "Close PlateMate";

    public static final String TO_LOGIN_BUTTON_LABEL = "Go to Login";

    public SignupViewModel() {
        super("sign up");
        setState(new SignupState());
    }

}
