package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;
    public static final int NEW_NOTE_ACTIVITY_REQUEST_CODE = 1;
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

                        // Delete the word
                        noteViewModel.delete(currNote);
                    }
                });

        helper.attachToRecyclerView(recyclerView);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewNote.class);
                startActivityForResult(intent, NEW_NOTE_ACTIVITY_REQUEST_CODE);
            }
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

        if (requestCode == NEW_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Note note = new Note(data.getStringExtra("reply note"));
            noteViewModel.insert(note);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "Empty String is not saved",
                    Toast.LENGTH_LONG).show();
        }
    }
}
