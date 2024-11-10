package use_case.user_profile;

import entity.UserFactory;

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
    public void execute(ProfileOutputData profileOutputData) {
        if (userDataAccessObject.existsByUsername(profileOutputData.getUsername())) {
            profilePresenter.prepareFailView("Profile already exists.");
        } else {
            final Profile profile = profileFactory.create(profileOutputData.getUsername(),
                    profileOutputData.getDietaryRestrictions(), profileOutputData.getAllergies(),
                    profileOutputData.getHealthGoals());
            userDataAccessObject.saveProfile(profile);

            profilePresenter.prepareSuccessView(profileOutputData);
        }

        @Override
        public void switchToProfileView() {
            profilePresenter.switchToProfileView();
        }
    }
}
