package use_case.healthy_reminders;

/**
 * The Input Data for the Profile Use Case.
 */

public class HealthyRemindersInputData {
    private final String username; // Optional if you want reminders personalized

    public HealthyRemindersInputData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}