package com.example.notesapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private NoteRepository noteRepository;
    private LiveData<List<Note>> notesList;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        noteRepository = new NoteRepository(application);
        notesList = noteRepository.getNotesList();
    }

    LiveData<List<Note>> getNotesList(){
        return notesList;
    }

    public void insert(Note note){
        noteRepository.insert(note);
    }

    public void delete(Note note){
        noteRepository.delete(note);
    }

    public void update(Note note){
        noteRepository.update(note);
    }
}
