//package use_case.healthy_reminders;
//
//public class HealthyRemindersOutputData {
//}
package use_case.healthy_reminders;

public class HealthyRemindersOutputData {
    private final String reminder;

    public HealthyRemindersOutputData(String reminder) {
        this.reminder = reminder;
    }

    public String getReminder() {
        return reminder;
    }
}
