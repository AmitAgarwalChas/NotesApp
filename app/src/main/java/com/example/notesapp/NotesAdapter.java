package com.example.notesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    private final LayoutInflater inflater;
    private static List<Note> notes;

    public NotesAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_rv, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        if(notes != null){
            Note note = notes.get(position);
            holder.noteItem.setText(note.getNote());
        }
    }

    void setNotes(List<Note> notes){
        this.notes = notes;
        notifyDataSetChanged();
    }

    public static Note getNote(int position){
        return notes.get(position);
    }

    @Override
    public int getItemCount() {
        if(notes != null) {
            return notes.size();
        }
        return 0;
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        private final TextView noteItem;
        LinearLayout lay;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            noteItem = itemView.findViewById(R.id.note_item);
            lay = itemView.findViewById(R.id.item_lay);
        }
    }
}
