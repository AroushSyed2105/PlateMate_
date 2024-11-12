package interface_adapter.profile;


import use_case.login.LoginInputBoundary;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInputData;
import use_case.user_profile.ProfileInputBoundary;
import use_case.user_profile.ProfileInputData;


public class ProfileController {
    private final ProfileInputBoundary profileUseCaseInteractor;

    public ProfileController(ProfileInputBoundary profileUseCaseInteractor) {
        this.profileUseCaseInteractor = profileUseCaseInteractor;
    }
    /**
     * Executes the Logout Use Case.
     * @param allergies is allgeries of the user
     * @param HealthyGoals are the goals that the user wants us to follow in health plan
     * @param DietaryRestrictions are any eating restrictions they have that we should keep account for
     */
    public void execute(String[] allergies, String[] HealthyGoals, String[] DietaryRestrictions, String username) {
        final ProfileInputData profileInputData = new ProfileInputData(allergies, HealthyGoals, DietaryRestrictions, username);
        profileUseCaseInteractor.execute(profileInputData);
    }
}
