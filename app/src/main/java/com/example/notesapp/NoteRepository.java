package com.example.notesapp;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {

    private NoteDAO noteDAO;
    private LiveData<List<Note>> notesList;

    NoteRepository(Application application){
        NoteRoomDatabase database = NoteRoomDatabase.getDatabase(application);
        noteDAO = database.noteDAO();
        notesList = noteDAO.getNotes();
    }

    LiveData<List<Note>> getNotesList(){
        return notesList;
    }

    void insert(final Note note){
        NoteRoomDatabase.databaseWriteExecutor.execute(() -> noteDAO.insert(note));
    }

    void delete(final Note note){
        NoteRoomDatabase.databaseWriteExecutor.execute( () -> noteDAO.delete(note) );
    }
}
