package interface_adapter.meal_plan;

/**
 * The state for the MealPlan View Model.
 */
public class MealPlanState {
    private String[] allergies = {};
    private String[] healthGoals = {};
    private String[] dietaryRestrictions = {};

    public String[] getAllergies() { return allergies; }

    public String[] getHealthGoals() { return healthGoals; }

    public String[] getDietaryRestrictions() { return dietaryRestrictions; }

    public void setAllergies(String[] allergies) { this.allergies = allergies; }

    public void setHealthGoals(String[] healthGoals) { this.healthGoals = healthGoals; }

    public void setDietaryRestrictions(String[] dietaryRestrictions) {this.dietaryRestrictions = dietaryRestrictions; }

}
