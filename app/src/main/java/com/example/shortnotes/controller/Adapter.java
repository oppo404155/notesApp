package com.example.shortnotes.controller;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.shortnotes.R;
import com.example.shortnotes.model.Note;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter extends RecyclerView.Adapter<Adapter.viewholder> implements Filterable {
    private List<Note> notes_list = new ArrayList<>();
    private List<Note> current_database_notes =new ArrayList<>();
    private List<Note> helper_List = new ArrayList<>();

    private OnItemClickLisner lisner;


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);


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

    public void setNotes_list(List<Note> notesList) {
        this.notes_list = notesList;
        notifyDataSetChanged();
       helper_List.addAll(notesList);
       current_database_notes.addAll(notesList);

    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Note> filteredNotes = new ArrayList<>();

            if (charSequence==null || charSequence.length() == 0) {
                filteredNotes.addAll(current_database_notes);
            } else {
                String filter_result = charSequence.toString().toLowerCase().trim();
               helper_List.forEach(note -> {
                    if (note.getTitle().toLowerCase().contains(filter_result)) {
                        filteredNotes.add(note);


                    }

                });
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredNotes;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            notes_list.clear();
            notes_list.addAll((Collection<? extends Note>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    class viewholder extends RecyclerView.ViewHolder {
        TextView priority;
        TextView title;
        TextView description;

        private viewholder(@NonNull View itemView) {
            super(itemView);
            priority = itemView.findViewById(R.id.priority_txv);
            title = itemView.findViewById(R.id.title_txv);
            description = itemView.findViewById(R.id.description_txv);
            itemView.setOnClickListener(view -> {
                if (lisner != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    lisner.OnItemClicked(notes_list.get(getAdapterPosition()));
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

    public void set_listItem(Note note) {
        notes_list.add(note);
        notifyDataSetChanged();


    }
    public void delet_item_fromList(Note note){
        notes_list.remove(note);
        notifyDataSetChanged();

    }



}
