package use_case.user_profile;

import entity.ProfileFactory;
import entity.Profile;

public class ProfileInteractor implements ProfileInputBoundary{
    private final ProfileUserDataAccessInterface userDataAccessObject;
    private final ProfileOutputBoundary profilePresenter;
    private final ProfileFactory profileFactory;

    public ProfileInteractor(ProfileUserDataAccessInterface profileUserDataAccessInterface,
                            ProfileOutputBoundary profileOutputBoundary,
                            ProfileFactory profileFactory) {
        this.userDataAccessObject = profileUserDataAccessInterface;
        this.profilePresenter = profileOutputBoundary;
        this.profileFactory = profileFactory;
    }


    @Override
    public void execute(ProfileInputData profileInputData) {
        if (userDataAccessObject.existsByUsername(profileInputData.getUsername())) {
            profilePresenter.prepareFailView("Profile already exists.");
        } else {

            final Profile profile = profileFactory.create(profileInputData.getAllergies(), profileInputData.getDietaryRestrictions(),
                    profileInputData.getHealthGoals(),profileInputData.getUsername());
            userDataAccessObject.saveProfile(profile);

            final ProfileOutputData profileOutputData = new ProfileOutputData(profileInputData.getAllergies(), profileInputData.getDietaryRestrictions(),
                    profileInputData.getHealthGoals(), profileInputData.getUsername());
            profilePresenter.prepareSuccessView(profileOutputData);
        }
    }

    @Override
    public void switchToMealPlanView() {
        profilePresenter.switchToMealPlanView();
    }

    @Override
    public void switchToGroceryView() {
        profilePresenter.switchToGroceryView();
    }

}
