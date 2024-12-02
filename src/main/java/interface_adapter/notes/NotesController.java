package interface_adapter.notes;

import use_case.notes.NotesInputBoundary;

public class NotesController {
    private final NotesInputBoundary notesUseCaseInteractor;
    public NotesController(NotesInputBoundary notesUseCaseInteractor) {
        this.notesUseCaseInteractor = notesUseCaseInteractor;
    }
    public void switchToMealPlanView() { notesUseCaseInteractor.switchToMealPlanView();}
}
