package use_case.user_profile;

import entity.User;
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
        }
        else {

            final User user = userFactory.create(profileOutputData.getUsername(), profileOutputData.getPassword());
            userDataAccessObject.save(user);

            final ProfileOutputData ProfileOutputData = new ProfileOutputData(user.getName(), false);
            profilePresenter.prepareSuccessView(signupOutputData);
        }
    }

    @Override
    public void switchToProfileView() {
        profilePresenter.switchToProfileView();
    }
}
