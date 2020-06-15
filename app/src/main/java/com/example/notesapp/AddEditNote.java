package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class AddEditNote extends AppCompatActivity {

    private TextInputEditText newNote;
    private Button saveNote;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        newNote = findViewById(R.id.et_new_note);
        saveNote = findViewById(R.id.btn_add);

        Intent intent = getIntent();
        if(intent.hasExtra("prev note")){
            newNote.setText(intent.getStringExtra("prev note"));
            id = intent.getIntExtra("note id", -1);
        }
        saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                if(TextUtils.isEmpty(newNote.getText())){
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String note = newNote.getText().toString();
                    replyIntent.putExtra("new note", note);
                    replyIntent.putExtra("edited note", note);
                    replyIntent.putExtra("Note id", id);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}
