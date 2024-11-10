package interface_adapter.profile;

public class ProfileState {
    private String username = "";
    private String loginError;
    private String password = "";
    private String[] allergies = {};
    private String[] healthGoals = {};
    private String[] dietaryRestrictions = {};

    public String getUsername() {
        return username;
    }

    public String getLoginError() {
        return loginError;
    }

    public String getPassword() {
        return password;
    }

    public String[] getAllergies() { return allergies; }

    public String[] getHealthGoals() { return healthGoals; }

    public String[] getDietaryRestrictions() { return dietaryRestrictions; }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setLoginError(String usernameError) {
        this.loginError = usernameError;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAllergies(String[] allergies) { this.allergies = allergies; }

    public void setHealthGoals(String[] healthGoals) { this.healthGoals = healthGoals; }

    public void setDietaryRestrictions(String[] dietaryRestrictions) { this.dietaryRestrictions = dietaryRestrictions; }

}