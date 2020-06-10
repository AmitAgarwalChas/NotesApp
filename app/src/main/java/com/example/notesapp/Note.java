package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes_table")
public class Note {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "note")
    private String mNote;

    public Note(@NonNull String note) {
        this.mNote = note;
    }

    @NonNull
    public String getNote() {
        return this.mNote;
    }
}
