package com.example.shortnotes.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.shortnotes.controller.Adapter;
import com.example.shortnotes.controller.my_viewmodel;
import com.example.shortnotes.model.Note;
import com.example.shortnotes.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements com.example.shortnotes.ui.dialog.myinterface, Adapter.OnItemClickLisner {
    private my_viewmodel viewmodel;
    FloatingActionButton button;
    dialog dialog;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_clear_all:
                viewmodel.delete_all_notes();
                Toast.makeText(getApplicationContext(), "all Notes are removed", Toast.LENGTH_LONG).show();
        }
        return true;
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        Adapter adapter = new Adapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        button = findViewById(R.id.float_bott);
        button.setOnClickListener(lisner -> {

            dialog = new dialog();
            dialog.show(getSupportFragmentManager(), "show dialog");
        });
        viewmodel = ViewModelProviders.of(this).get(my_viewmodel.class);
        viewmodel.get_all_notes().observe(this, notes -> {
            adapter.setNotes_list(notes);

        });
        adapter.setitemclicklistner(this);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {


                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewmodel.delete(adapter.getNoteAtPosition(viewHolder.getAdapterPosition()));
                Toast.makeText(getApplicationContext(), "Note has been deleted", Toast.LENGTH_LONG).show();

            }
        }).attachToRecyclerView(recyclerView);


    }


    @Override
    public void connection(Note note) {
        viewmodel.insert(note);

    }

    @Override
    public void OnItemClicked(Note note) {
        int id = note.getId();
        Toast.makeText(getApplicationContext(), "updated", Toast.LENGTH_LONG).show();
        Note mnote = new Note("HELLO", note.getDescription(), note.getPriority());
        mnote.setId(id);

        viewmodel.update(mnote);

    }
}
