package use_case.user_profile;

import entity.ProfileFactory;
import entity.Profile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProfileInteractor implements ProfileInputBoundary{
    private static final String[] VALID_ALLERGIES = List.of("None", "Almonds", "Brazil nuts", "Cashews", "Hazelnuts", "Macadamia nuts",
            "Pecans", "Pine nuts", "Pistachios", "Walnuts", "Peanuts", "Shrimp", "Lobster", "Crab", "Prawns", "Crayfish",
            "Clams", "Oysters", "Scallops", "Mussels", "Squid", "Octopus", "Salmon", "Tuna", "Cod", "Haddock",
            "Mackerel", "Halibut", "Milk (Cow, Goat, Sheep)", "Cheese", "Yogurt", "Butter", "Cream", "Ghee", "Egg whites",
            "Egg yolks", "Wheat", "Rye", "Barley", "Oats", "Corn (Maize)", "Soybeans", "Soy milk", "Tofu", "Tempeh",
            "Soy protein isolates", "Apples", "Bananas", "Kiwi", "Peaches", "Cherries", "Avocado", "Strawberries",
            "Oranges", "Celery", "Carrots", "Potatoes", "Bell peppers", "Tomatoes", "Lentils", "Chickpeas", "Green peas",
            "Beans (Kidney beans, Black beans)", "Sesame seeds", "Sunflower seeds", "Mustard seeds", "Poppy seeds",
            "Flaxseeds (Linseeds)", "Beef", "Pork", "Chicken", "Turkey", "Lamb", "Buckwheat", "Seaweed", "Mango",
            "Lotus seeds", "Durian", "Lupin", "Mustard", "Cassava", "Plantains", "Baobab fruit", "Chickpeas",
            "Pistachios", "Dates", "Quinoa", "Amaranth", "Corn", "Chia seeds", "Red meat", "Gelatin", "Cinnamon",
            "Nutmeg", "Curry powder", "Paprika", "Mushrooms", "Processed soy products (Soy lecithin)",
            "Hydrolyzed wheat proteins", "Milk proteins in baked goods").toArray(new String[0]);
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

    public String[] processSelectedAllergies(String[] selectedAllergies) {
        List<String> validAllergies = new ArrayList<>();

        for (String allergy : selectedAllergies) {
            for (String validAllergy: VALID_ALLERGIES) {
                if (validAllergy.contains(allergy)) {
                    validAllergies.add(allergy);
                }
            }
        }
        return validAllergies.toArray(new String[0]);
    }

    @Override
    public void execute(ProfileInputData profileInputData) {
        if (userDataAccessObject.existsByUsername(profileInputData.getUsername())) {
            profilePresenter.prepareFailView("Profile already exists.");
        } else {

            String[] validAllergies = processSelectedAllergies(profileInputData.getAllergies());

            final Profile profile = profileFactory.create( validAllergies, profileInputData.getDietaryRestrictions(),
                    profileInputData.getHealthGoals(),profileInputData.getUsername());
            userDataAccessObject.saveProfile(profile);

            final ProfileOutputData profileOutputData = new ProfileOutputData(validAllergies, profileInputData.getDietaryRestrictions(),
                    profileInputData.getHealthGoals(), profileInputData.getUsername());
            profilePresenter.prepareSuccessView(profileOutputData);
        }
    }

    @Override
    public void switchToMealPlanView() {
        profilePresenter.switchToMealPlanView();
    }


}
