package use_case.healthy_reminders;

import java.io.IOException;

public interface HealthyRemindersInputBoundary {

    /**
     * Executes the profile use case.
     */
    String generateReminder() throws IOException;

    void execute(HealthyRemindersInputData healthyRemindersInputData) throws IOException;

    void switchToLoggedInView();
}
//package use_case.healthy_reminders;
//
//public interface HealthyRemindersInputBoundary {
//    String generateReminder();
//}
