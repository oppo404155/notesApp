package com.example.shortnotes.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.shortnotes.R;
import com.example.shortnotes.controller.Adapter;
import com.example.shortnotes.controller.my_viewmodel;
import com.example.shortnotes.controller.service;
import com.example.shortnotes.model.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements com.example.shortnotes.ui.dialog.myinterface,
        Adapter.OnItemClickLisner {
    private my_viewmodel viewmodel;
    FloatingActionButton button;
    dialog dialog;
    CoordinatorLayout coordinatorLayout;
    Adapter adapter;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

            if(item.getItemId()==R.id.menu_clear_all) {

                viewmodel.delete_all_notes();
                Toast.makeText(getApplicationContext(), "all Notes are removed", Toast.LENGTH_LONG).show();
            }

        return true;
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coordinatorLayout = findViewById(R.id.coordinator);
        setSupportActionBar(findViewById(R.id.toolbar));
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        adapter = new Adapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        button = findViewById(R.id.float_bott);
        button.setOnClickListener(listner -> {
            Intent intent = new Intent(this, service.class);
            intent.putExtra(service.Extra, "hello");
            startService(intent);
            dialog = new dialog();
            dialog.show(getSupportFragmentManager(), "show dialog");
        });
        viewmodel = ViewModelProviders.of(this).get(my_viewmodel.class);
        viewmodel.get_all_notes().observe(this, notes -> adapter.setNotes_list(notes));
        adapter.setitemclicklistner(this);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {


                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                viewmodel.delete(adapter.getNoteAtPosition(viewHolder.getAdapterPosition()));
                creatnotification();
                Toast.makeText(getApplicationContext(), "note has been deleted", Toast.LENGTH_LONG).show();


            }
        }).attachToRecyclerView(recyclerView);


    }


    @Override
    public void connection(Note note) {
        if (note.getId() == 0) {
            viewmodel.insert(note);
            Toast.makeText(getApplicationContext(), "note has been added", Toast.LENGTH_LONG).show();
        } else {
            viewmodel.update(note);
            Toast.makeText(getApplicationContext(), "note has been updated", Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void OnItemClicked(Note note) {
        dialog = new dialog();
        dialog.set_note_flag(note.getId());
        dialog.show(getSupportFragmentManager(), "show dialog");


    }


    // JUST FOR TESTING NOTIFICATIONS

    public void creatnotification() {

        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), app.CHANNEL_ID);
        notification.setSmallIcon(R.drawable.favorites_24dp).setContentTitle("updating")
                .setContentText("there is updating in Notes database");
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(1, notification.build());


    }


}
