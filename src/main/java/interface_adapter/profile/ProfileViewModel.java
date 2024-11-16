package interface_adapter.profile;

import interface_adapter.ViewModel;

public class ProfileViewModel extends ViewModel<ProfileState> {
    public static final String TITLE_LABEL = "Profile";
    public static final String ALLERGIES = "Allergies";
    public static final String DIETARY_RESTRICTIONS = "Restrictions";
    public static final String HEALTH_GOALS = "Health Goals";
    public static final String CANCEL_BUTTON_LABEL = "Cancel";
    public static final String SAVE_BUTTON_LABEL = "Save";


    public ProfileViewModel() {
        super("Profile");
        setState(new ProfileState());
    }
}
