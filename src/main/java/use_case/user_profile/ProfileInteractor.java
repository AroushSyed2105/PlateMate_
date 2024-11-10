package use_case.user_profile;

import entity.UserFactory;
import use_case.signup.SignupOutputData;

public class ProfileInteractor {
    private final ProfileUserDataAccessInterface userDataAccessObject;
    private final ProfileOutputBoundary profilePresenter;
    private final UserFactory userFactory;

    public ProfileInteractor(ProfileUserDataAccessInterface profileUserDataAccessInterface,
                            ProfileOutputBoundary profileOutputBoundary,
                            UserFactory userFactory) {
        this.userDataAccessObject = profileUserDataAccessInterface;
        this.profilePresenter = profileOutputBoundary;
        this.userFactory = userFactory;
    }

    @Override
    public void execute(ProfileInputData profileInputData) {
        if (userDataAccessObject.existsByUsername(profileInputData.getUsername())) {
            profilePresenter.prepareFailView("Profile already exists.");
        } else {
            final Profile profile = profileFactory.create(profileInputData.getUsername(),
                    profileInputData.getDietaryRestrictions(), profileInputData.getAllergies(),
                    profileInputData.getHealthGoals());
            userDataAccessObject.saveProfile(profile);

            final ProfileOutputData profileOutputData = new ProfileOutputData();
            profilePresenter.prepareSuccessView(profileOutputData);
        }

        @Override
        public void switchToProfileView() {
            profilePresenter.switchToProfileView();
        }
    }
}
