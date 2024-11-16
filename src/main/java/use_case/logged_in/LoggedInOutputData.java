package use_case.logged_in;

public class LoggedInOutputData {

    private final String username;
    private final boolean useCaseFailed;

    public LoggedInOutputData(String username, boolean useCaseFailed) {
        this.username = username;
        this.useCaseFailed = useCaseFailed;
    }

    public String getUsername() {
        return username;
    }
}
