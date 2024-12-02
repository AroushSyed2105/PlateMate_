package interface_adapter.notes;

/**
 * The state for the Note View Model.
 */
public class NoteState {

    private static volatile NoteState instance;

    private String savedNotes = "";

    NoteState() {}

    public static NoteState getInstance() {
        if (instance == null) {
            synchronized (NoteState.class) {
                if (instance == null) {
                    instance = new NoteState();
                }
            }
        }
        return instance;
    }

    public String getSavedNotes() { return savedNotes; }
    // Setter for savedNotes
    public void setSavedNotes(String savedNotes) { this.savedNotes = savedNotes;}
}
