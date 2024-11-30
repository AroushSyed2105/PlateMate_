//package use_case.healthy_reminders;
//
//import app.ChatGPTPost;
//import app.ChatPost;
//
//public class HealthyRemindersInteractor implements HealthyRemindersInputBoundary {
//    private final HealthyRemindersUserDataAccessInterface userDataAccessObject;
//    private final HealthyRemindersOutputBoundary outputBoundary;
//    private final ChatPost chatGPTPost;
//
//    public HealthyRemindersInteractor(
//            HealthyRemindersUserDataAccessInterface userDataAccessObject,
//            HealthyRemindersOutputBoundary outputBoundary,
//            ChatPost chatGPTPost // Use ChatGPTPost here
//    ) {
//        this.userDataAccessObject = userDataAccessObject;
//        this.outputBoundary = outputBoundary;
//        this.chatGPTPost = chatGPTPost;
//    }
//
//    @Override
//    public void execute(HealthyRemindersInputData healthyRemindersInputData) {
//        String username = healthyRemindersInputData.getUsername();
//        String reminder = generateReminder();
//
//        if (reminder != null && !reminder.isEmpty()) {
//            HealthyRemindersOutputData outputData = new HealthyRemindersOutputData(reminder);
//            outputBoundary.prepareSuccessView(outputData);
//        } else {
//            outputBoundary.prepareFailView("Failed to generate a reminder.");
//        }
//    }
//
//    @Override
//    public String generateReminder() {
//        String prompt = "Generate a daily healthy reminder.";
//        System.out.println("Prompt sent to ChatGPTPost: " + prompt);
//        String reminder = null;
//        try {
//            reminder = chatGPTPost.getChatGPTResponse(prompt); // Call ChatGPT API
//        } catch (Exception e) {
//            System.err.println("Error while fetching reminder: " + e.getMessage());
//        }
//        if (reminder == null || reminder.isEmpty()) {
//            System.out.println("Reminder generation failed or returned empty.");
//            return null;
//        }
//        System.out.println("Generated reminder: " + reminder);
//        return reminder;
//    }
//}

package use_case.healthy_reminders;

import app.ChatPost;
import interface_adapter.healthyreminders.HealthyRemindersPresenter;

public class HealthyRemindersInteractor implements HealthyRemindersInputBoundary {
    private final HealthyRemindersUserDataAccessInterface userDataAccessObject;
    private final HealthyRemindersOutputBoundary healthyRemindersPresenter;
    private final ChatPost chatPost;


    public HealthyRemindersInteractor(
            HealthyRemindersUserDataAccessInterface userDataAccessObject,
            HealthyRemindersOutputBoundary healthyRemindersOutputBoundary,
            ChatPost chatPost
    ) {
        this.userDataAccessObject = userDataAccessObject;
        this.healthyRemindersPresenter = healthyRemindersOutputBoundary;
        this.chatPost = chatPost;
    }

    @Override
    public void execute(HealthyRemindersInputData healthyRemindersInputData) {
        String username = healthyRemindersInputData.getUsername();
        String reminder = generateReminder();

        if (reminder != null && !reminder.isEmpty()) {
            HealthyRemindersOutputData outputData = new HealthyRemindersOutputData(reminder);
            healthyRemindersPresenter.prepareSuccessView(outputData);
        } else {
            healthyRemindersPresenter.prepareFailView("Failed to generate a reminder.");
        }
    }
    @Override
    public String generateReminder() {
        String prompt = "Generate a daily healthy reminder.";
        System.out.println("Prompt sent to ChatPost: " + prompt);
        String reminder = chatPost.getResponse(prompt);
        if (reminder == null || reminder.isEmpty()) {
            System.out.println("Reminder generation failed or returned empty.");
            return null;
        }
        System.out.println("Generated reminder: " + reminder);
        return reminder;
    }

    public void switchToLoggedInView() { healthyRemindersPresenter.switchToLoggedInView();}

}
