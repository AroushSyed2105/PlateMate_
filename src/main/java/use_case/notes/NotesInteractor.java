package use_case.notes;

public class NotesInteractor implements use_case.notes.NotesInputBoundary {
    private final NotesOutputBoundary notesPresenter;

    public NotesInteractor(NotesOutputBoundary notesPresenter) {
        this.notesPresenter = notesPresenter;
    }
    public void switchToMealPlanView() { notesPresenter.switchToMealPlanView(); }
    }

