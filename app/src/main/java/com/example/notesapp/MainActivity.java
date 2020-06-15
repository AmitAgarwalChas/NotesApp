package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;
    public static final int NEW_NOTE_REQUEST_CODE = 1;
    public static final int EDIT_NOTE_REQUEST_CODE = 2;
    public static Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        NotesAdapter notesAdapter = new NotesAdapter(this);
        recyclerView.setAdapter(notesAdapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.getNotesList().observe(this, notes -> notesAdapter.setNotes(notes));

        // deleting a note
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                         int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Note currNote = NotesAdapter.getNote(position);
                        if(toast != null){
                            toast.cancel();
                        }
                        toast = Toast.makeText(MainActivity.this, "Deleted " +
                                currNote.getNote(), Toast.LENGTH_LONG);
                        toast.show();

                        // Delete the note
                        noteViewModel.delete(currNote);
                    }
                });

        helper.attachToRecyclerView(recyclerView);

        //Adding a new Note
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEditNote.class);
                startActivityForResult(intent, NEW_NOTE_REQUEST_CODE);
            }
        });

        //Updating a Note.
        notesAdapter.setOnItemClickListener(note -> {
            Intent intent = new Intent(MainActivity.this, AddEditNote.class);
            intent.putExtra("prev note", note.getNote());
            intent.putExtra("note id", note.getId());
            startActivityForResult(intent, EDIT_NOTE_REQUEST_CODE);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (toast != null)
            toast.cancel();
    }

    @Override
    protected void onStop(){
        super.onStop();
        if (toast != null)
            toast.cancel();
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_NOTE_REQUEST_CODE && resultCode == RESULT_OK) {
            Note note = new Note(data.getStringExtra("new note"));
            noteViewModel.insert(note);
        } else if(requestCode == EDIT_NOTE_REQUEST_CODE && resultCode == RESULT_OK){
            int id = data.getIntExtra("Note id", -1);
            if(id == 1){
                Toast.makeText(
                        getApplicationContext(),
                        "Note can't be updated",
                        Toast.LENGTH_SHORT).show();
            }
            Note note = new Note(data.getStringExtra("edited note"), id);
            noteViewModel.update(note);
        }else {
            Toast.makeText(
                    getApplicationContext(),
                    "Empty String cannot be saved",
                    Toast.LENGTH_LONG).show();
        }
    }
}
