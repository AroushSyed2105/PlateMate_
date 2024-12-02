package interface_adapter.notes;

import interface_adapter.ViewModel;

public class NotesViewModel extends ViewModel<NoteState> {
    public NotesViewModel() {
        super("Notes");
        setState(new NoteState());
    }
}
