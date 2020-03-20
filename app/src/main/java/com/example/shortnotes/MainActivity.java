package com.example.shortnotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements dialog.myinterface, Adapter.OnItemClickLisner {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        Adapter adapter = new Adapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        button = findViewById(R.id.float_bott);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new dialog();
                dialog.show(getSupportFragmentManager(), "show dialog");

            }
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
                Toast.makeText(getApplicationContext(), "Note deleted", Toast.LENGTH_LONG).show();

            }
        }).attachToRecyclerView(recyclerView);


    }


    @Override
    public void connection(Note note) {
        viewmodel.insert(note);

    }

    @Override
    public void OnItemClicked(Note note) {
        Toast.makeText(getApplicationContext(), "updated", Toast.LENGTH_LONG).show();

    }
}
