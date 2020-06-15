package com.example.notesapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Note note);

    @Update
    void update(Note... note);

    @Query("DELETE FROM notes_table")
    void deleteAll();

    @Delete
    void delete(Note note);

    @Query("SELECT * FROM notes_table")
    LiveData<List<Note>> getNotes();
}
