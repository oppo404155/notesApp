package com.example.shortnotes.controller;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shortnotes.model.Note;
import com.example.shortnotes.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter extends RecyclerView.Adapter<Adapter.viewholder> {
    List<Note> notes_list = new ArrayList<>();
    OnItemClickLisner lisner;

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, null, false);

        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        Note note = notes_list.get(position);
        holder.description.setText(note.getDescription());
        holder.priority.setText("" + note.getPriority());
        holder.title.setText(note.getTitle());


    }

    @Override
    public int getItemCount() {
        return notes_list.size();
    }

    public void setNotes_list(List<Note> notes_list) {
        this.notes_list = notes_list;
        notifyDataSetChanged();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView priority;
        TextView title;
        TextView description;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            priority = itemView.findViewById(R.id.priority_txv);
            title = itemView.findViewById(R.id.title_txv);
            description = itemView.findViewById(R.id.description_txv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (lisner != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                        lisner.OnItemClicked(notes_list.get(getAdapterPosition()));
                    }
                }
            });


        }
    }

    public Note getNoteAtPosition(int position) {

        return notes_list.get(position);

    }

    public interface OnItemClickLisner {
        void OnItemClicked(Note note);
    }

    public void setitemclicklistner(OnItemClickLisner lisner) {
        this.lisner = lisner;

    }

    public static class my_viewmodel extends AndroidViewModel {
        private LiveData<List<Note>> listLiveData;
        private Note_Repository repository;

        public my_viewmodel(@NonNull Application application) {
            super(application);
            repository = new Note_Repository(application);
            listLiveData = repository.getAllnotes();
        }

        public void insert(Note note) {
            repository.insert(note);

        }

        public void update(Note note) {
            repository.updaet(note);

        }

        public void delete(Note note) {
            repository.delete(note);

        }

        public void delete_all_notes() {
            repository.delete_all_notes();

        }

        public LiveData<List<Note>> get_all_notes() {

            return listLiveData;
        }

    }
}
