package interface_adapter.healthyreminders;

public class HealthyRemindersState {
    private String currentReminder = "";

    public String getCurrentReminder() {
        return currentReminder;
    }

    public void setCurrentReminder(String currentReminder) {
        this.currentReminder = currentReminder;
    }
}

//package interface_adapter.healthyreminders;
//
///**
// * Represents the state for the Healthy Reminders functionality.
// */
//public class HealthyRemindersState {
//    private String currentReminder = "";
//
//    /**
//     * Gets the current healthy reminder.
//     *
//     * @return the current reminder.
//     */
//    public String getCurrentReminder() {
//        return currentReminder;
//    }
//
//    /**
//     * Sets the current healthy reminder.
//     *
//     * @param currentReminder the reminder to set.
//     */
//    public void setCurrentReminder(String currentReminder) {
//        this.currentReminder = currentReminder;
//    }
//
//    /**
//     * Resets the state by clearing the current reminder.
//     */
//    public void resetState() {
//        this.currentReminder = "";
//    }
//}

