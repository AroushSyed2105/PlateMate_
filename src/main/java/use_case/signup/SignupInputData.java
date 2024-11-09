package use_case.signup;

/**
 * The Input Data for the Signup Use Case.
 */
public class SignupInputData {

    private final String username;
    private final String password;
    private final String repeatPassword;
    private final String email;
    private final String address;

    public SignupInputData(String username, String password, String repeatPassword, String email, String address) {
        this.username = username;
        this.password = password;
        this.repeatPassword = repeatPassword;
        this.email = email;
        this.address = address;
    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }

    String getEmail() {
        return email;
    }

    String getAddress() {
        return address;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }
}
