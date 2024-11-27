package use_case.healthy_reminders;

import app.ChatPost;

public class HealthyRemindersInteractor implements HealthyRemindersInputBoundary {
    private final HealthyRemindersUserDataAccessInterface userDataAccessObject;
    private final HealthyRemindersOutputBoundary outputBoundary;
    private final ChatPost chatPost;

    public HealthyRemindersInteractor(
            HealthyRemindersUserDataAccessInterface userDataAccessObject,
            HealthyRemindersOutputBoundary outputBoundary,
            ChatPost chatPost
    ) {
        this.userDataAccessObject = userDataAccessObject;
        this.outputBoundary = outputBoundary;
        this.chatPost = chatPost;
    }

    @Override
    public void execute(HealthyRemindersInputData healthyRemindersInputData) {
        String username = healthyRemindersInputData.getUsername();
        String reminder = generateReminder();

        if (reminder != null && !reminder.isEmpty()) {
            HealthyRemindersOutputData outputData = new HealthyRemindersOutputData(reminder);
            outputBoundary.prepareSuccessView(outputData);
        } else {
            outputBoundary.prepareFailView("Failed to generate a reminder.");
        }
    }

    @Override
    public String generateReminder() {
        String prompt = "Generate a random daily healthy reminder.";
        String reminder = chatPost.getResponse(prompt);
        HealthyRemindersOutputData outputData = new HealthyRemindersOutputData(reminder);
        outputBoundary.prepareSuccessView(outputData);
        return reminder;
    }

}
