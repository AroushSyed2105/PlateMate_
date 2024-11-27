package app;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import data_access.InMemoryUserDataAccessObject;
import entity.CommonUserFactory;
import entity.ProfileFactory;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.change_password.ChangePasswordController;
import interface_adapter.change_password.ChangePasswordPresenter;
import interface_adapter.change_password.LoggedInViewModel;
import interface_adapter.logged_in.LoggedInController;
import interface_adapter.logged_in.LoggedInPresenter;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.meal_plan.MealPlanViewModel;
import interface_adapter.groceries.GroceryViewModel;
import interface_adapter.profile.*;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import use_case.change_password.ChangePasswordInputBoundary;
import use_case.change_password.ChangePasswordInteractor;
import use_case.change_password.ChangePasswordOutputBoundary;
import use_case.logged_in.LoggedInInputBoundary;
import use_case.logged_in.LoggedInInteractor;
import use_case.logged_in.LoggedInOutputBoundary;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import use_case.user_profile.ProfileInputBoundary;
import use_case.user_profile.ProfileOutputBoundary;
import use_case.user_profile.ProfileInteractor;
import view.LoggedInView;
import view.LoginView;
import view.SignupView;
import view.ViewManager;
import view.ProfileView;
import view.MealView;
import view.GroceryView;

/**
 * The AppBuilder class is responsible for putting together the pieces of
 * our CA architecture; piece by piece.
 * <p/>
 * This is done by adding each View and then adding related Use Cases.
 */
// Checkstyle note: you can ignore the "Class Data Abstraction Coupling"
//                  and the "Class Fan-Out Complexity" issues for this lab; we encourage
//                  your team to think about ways to refactor the code to resolve these
//                  if your team decides to work with this as your starter code
//                  for your final project this term.
public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    // thought question: is the hard dependency below a problem?
    private final UserFactory userFactory = new CommonUserFactory();
    private final ProfileFactory profileFactory = new CommonUserFactory();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    // thought question: is the hard dependency below a problem?
    private final InMemoryUserDataAccessObject userDataAccessObject = new InMemoryUserDataAccessObject();

    private SignupView signupView;
    private SignupViewModel signupViewModel;
    private LoginViewModel loginViewModel;
    private LoggedInViewModel loggedInViewModel;
    private LoggedInView loggedInView;
    private LoginView loginView;
    private ProfileViewModel profileViewModel;
    private ProfileView profileView;
    private MealPlanViewModel mealPlanViewModel;
    private MealView mealPlanView;
    private GroceryViewModel groceryViewModel;
    private GroceryView groceryView;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    /**
     * Adds the Signup View to the application.
     * @return this builder
     */
    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        cardPanel.add(signupView, signupView.getViewName());
        return this;
    }

    /**
     * Adds the Login View to the application.
     * @return this builder
     */
    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    /**
     * Adds the LoggedIn View to the application.
     * @return this builder
     */
    public AppBuilder addLoggedInView() {
        loggedInViewModel = new LoggedInViewModel();
        profileViewModel = new ProfileViewModel();
        mealPlanViewModel = new MealPlanViewModel();
        groceryViewModel = new GroceryViewModel();
        loggedInView = new LoggedInView(profileViewModel, loggedInViewModel);
        cardPanel.add(loggedInView, loggedInView.getViewName());
        return this;
    }

    /**
     * Adds the Profile View to the application.
     * @return this builder
     */
    public AppBuilder addProfileView() {
        profileViewModel = new ProfileViewModel();
        profileView = new ProfileView(profileViewModel);
        mealPlanViewModel = new MealPlanViewModel();
        groceryViewModel = new GroceryViewModel();
        cardPanel.add(profileView, profileView.getViewName());
        return this;
    }

    public AppBuilder addMealPlanView() {
        mealPlanViewModel = new MealPlanViewModel();
        mealPlanView = new MealView();
        cardPanel.add(mealPlanView, mealPlanView.getViewName());
        return this;
    }

    public AppBuilder addGroceryPlanView() {
        groceryViewModel = new GroceryViewModel();
        groceryView = new GroceryView();
        cardPanel.add(groceryView, groceryView.getViewName());
        return this;
    }

    /**
     * Adds the Signup Use Case to the application.
     * @return this builder
     */
    public AppBuilder addSignupUseCase() {
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel,
                signupViewModel, loginViewModel);
        final SignupInputBoundary userSignupInteractor = new SignupInteractor(
                userDataAccessObject, signupOutputBoundary, userFactory);

        final SignupController controller = new SignupController(userSignupInteractor);
        signupView.setSignupController(controller);
        return this;
    }

    public AppBuilder addLoggedInUseCase() {
        final LoggedInOutputBoundary loggedInOutputBoundary = new LoggedInPresenter(viewManagerModel,
                loggedInViewModel, profileViewModel);

        final LoggedInInputBoundary loggedInInteractor = new LoggedInInteractor(
               userDataAccessObject, loggedInOutputBoundary, userFactory);

        final LoggedInController controller = new LoggedInController(loggedInInteractor);
        loggedInView.setLoggedInController(controller);
        return this;

    }

    /**
     * Adds the Login Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLoginUseCase() {
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel,
                loggedInViewModel, loginViewModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary);

        final LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    public AppBuilder addProfileUseCase() {
        final ProfileOutputBoundary profileOutputBoundary = new ProfilePresenter(viewManagerModel, loggedInViewModel,
                mealPlanViewModel, profileViewModel, groceryViewModel);
        final ProfileInputBoundary profileInteractor = new ProfileInteractor(userDataAccessObject,
                profileOutputBoundary, profileFactory);

         final ProfileController profileController = new ProfileController(profileInteractor);
         loggedInView.setProfileController(profileController);
         profileView.setProfileController(profileController);
         return this;
    }

    /**
     * Adds the Change Password Use Case to the application.
     * @return this builder
     */
    public AppBuilder addChangePasswordUseCase() {
        final ChangePasswordOutputBoundary changePasswordOutputBoundary =
                new ChangePasswordPresenter(loggedInViewModel);

        final ChangePasswordInputBoundary changePasswordInteractor =
                new ChangePasswordInteractor(userDataAccessObject, changePasswordOutputBoundary, userFactory);

        final ChangePasswordController changePasswordController =
                new ChangePasswordController(changePasswordInteractor);
        loggedInView.setChangePasswordController(changePasswordController);
        return this;
    }

    /**
     * Adds the Logout Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLogoutUseCase() {
        final LogoutOutputBoundary logoutOutputBoundary = new LogoutPresenter(viewManagerModel,
                loggedInViewModel, loginViewModel);

        final LogoutInputBoundary logoutInteractor =
                new LogoutInteractor(userDataAccessObject, logoutOutputBoundary);

        final LogoutController logoutController = new LogoutController(logoutInteractor);
        loggedInView.setLogoutController(logoutController);
        return this;
    }

    /**
     * Creates the JFrame for the application and initially sets the SignupView to be displayed.
     * @return the application
     */
    public JFrame build() {
        final JFrame application = new JFrame("Login Example");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        viewManagerModel.setState(signupView.getViewName());
        viewManagerModel.firePropertyChanged();

        return application;
    }
}
