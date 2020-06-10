package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class NewNote extends AppCompatActivity {

    private TextInputEditText newNote;
    private Button addNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        newNote = findViewById(R.id.et_new_note);
        addNote = findViewById(R.id.btn_add);
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();

                if(TextUtils.isEmpty(newNote.getText())){
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String word = newNote.getText().toString();
                    replyIntent.putExtra("reply note", word);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}
