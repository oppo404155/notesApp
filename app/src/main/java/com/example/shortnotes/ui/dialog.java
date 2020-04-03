package com.example.shortnotes.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.shortnotes.R;
import com.example.shortnotes.model.Note;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class dialog extends DialogFragment {
    private  EditText Title;
    private  EditText Description;

    private  Note note;
    private int current_priority;
    private int note_flag=0 ;



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.custom_dialog, null, false);
        NumberPicker picker = view.findViewById(R.id.num_picker);
        Title = view.findViewById(R.id.custom_title);
        Description = view.findViewById(R.id.custom_desc);
        myinterface myinterface = (dialog.myinterface) getContext();
        picker.setMaxValue(10);
        picker.setMinValue(1);
        picker.setOnValueChangedListener((numberPicker, i, i1) -> current_priority = i1);
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(view).setTitle("Create new Note or update one")
                .setPositiveButton("Add/update", (dialogInterface, i) -> {

                    if (Title.getText().toString().isEmpty() || Description.getText().toString().isEmpty()) {

                      Toast.makeText(getContext(),"Error please enter the whole fields",Toast.LENGTH_LONG).show();

                    } else {
                        if (note_flag==0) {

                            note = new Note(Title.getText().toString(), Description.getText().toString(), current_priority);
                            assert myinterface != null;
                            myinterface.connection(note);
                        }
                        else {
                            note = new Note(Title.getText().toString(), Description.getText().toString(), current_priority);
                            note.setId(note_flag);
                            assert myinterface != null;
                            myinterface.connection(note);

                        }


                    }

                }).setNegativeButton("Discard", (dialogInterface, i) -> {


                });


        return alert.create();
    }


    public interface myinterface {
        void connection(Note note);

    }
    void set_note_flag(int flag){
        this.note_flag=flag;
    }
}
