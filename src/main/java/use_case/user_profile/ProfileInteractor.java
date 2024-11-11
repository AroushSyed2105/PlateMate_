package use_case.user_profile;

import entity.ProfileFactory;
import entity.Profile;

import javax.swing.*;
import java.util.List;

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
            final Profile profile = profileFactory.create( profileInputData.getAllergies(), profileInputData.getDietaryRestrictions(),
                    profileInputData.getHealthGoals(),profileInputData.getUsername());
            userDataAccessObject.saveProfile(profile);

            final ProfileOutputData profileOutputData = new ProfileOutputData(profileInputData.getAllergies(), profileInputData.getDietaryRestrictions(),
                    profileInputData.getHealthGoals(),profileInputData.getUsername());
            profilePresenter.prepareSuccessView(profileOutputData);
        }
    }

    public List<String> showDietaryRestrictionsSelection() {
        String [] restrictions = {"Vegan", "Vegetarian", "Gluten-free", "Dairy-free", "Nut-free", "Halal", "Kosher"};

        JList<String> restrictionsList = new JList<>(restrictions);
        restrictionsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JScrollPane scrollPane = new JScrollPane(restrictionsList);

        int result = JOptionPane.showConfirmDialog(null, scrollPane, "Select Dietary Restrictions",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            return restrictionsList.getSelectedValuesList();
        } else {
            return List.of();
        }
    }
}
