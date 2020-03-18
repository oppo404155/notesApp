package com.example.shortnotes;

import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

public class dialog extends DialogFragment {
    NumberPicker picker;
    EditText Title;
    EditText Description;
    myinterface myinterface = (dialog.myinterface) getContext();
    Note note;
    int current_priority;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.custom_dialog, null, false);
        picker = view.findViewById(R.id.num_picker);
        Title = view.findViewById(R.id.custom_title);
        Description = view.findViewById(R.id.custom_desc);
        picker.setMaxValue(10);
        picker.setMinValue(1);
        picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                current_priority = i1;
            }
        });
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(view).setTitle("Create new Note")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (Title.getText().toString().isEmpty() || Description.getText().toString().isEmpty()) {
                            Toast.makeText(getContext(), "error please enter the whole fields", Toast.LENGTH_LONG).show();

                        } else {
                            note = new Note(Title.getText().toString(), Description.getText().toString(), current_priority);
                            myinterface.connection(note);


                        }

                    }
                }).setNegativeButton("Discard", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        });


        return alert.create();
    }

    public interface myinterface {
        void connection(Note note);

    }
}
