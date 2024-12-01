package use_case.healthy_reminders;

public interface HealthyRemindersOutputBoundary {
    /**
     * Prepares the success view with the generated reminder.
     * @param response the response data containing the generated reminder
     */
    void prepareSuccessView(HealthyRemindersOutputData response);

    /**
     * Prepares the fail view in case the reminder generation fails.
     * @param error the error message to display
     */
    void prepareFailView(String error);

    /**
     * Switches to the Logged In view.
     */
    void switchToLoggedInView();
}


