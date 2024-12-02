package interface_adapter.notes;

/**
 * The state for the MealPlan View Model.
 */
public class NoteState {
    private String savedNotes = "";

    public String getSavedNotes() {
        return savedNotes;
    }

    public void setSavedNotes(String savedNotes) {
        this.savedNotes = savedNotes;
    }
}