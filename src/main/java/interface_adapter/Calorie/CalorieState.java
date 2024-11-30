package interface_adapter.Calorie;

public class CalorieState {
    private int[] actualCalories;
    private int[] plannedCalories;
    private String message;

    public int[] getActualCalories() {
        return actualCalories;
    }

    public void setActualCalories(int[] actualCalories) {
        this.actualCalories = actualCalories;
    }

    public int[] getPlannedCalories() {
        return plannedCalories;
    }

    public void setPlannedCalories(int[] plannedCalories) {
        this.plannedCalories = plannedCalories;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
