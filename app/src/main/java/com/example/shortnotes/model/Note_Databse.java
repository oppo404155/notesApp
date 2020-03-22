package com.example.shortnotes.model;

import android.content.Context;
import android.os.AsyncTask;

import com.example.shortnotes.controller.note_data_access_opject;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = Note.class, version = 1)
public abstract class Note_Databse extends RoomDatabase {
    private static Note_Databse instance;

    public abstract note_data_access_opject dao();

    public static synchronized Note_Databse getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), Note_Databse.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomcallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomcallback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new populating_database(instance).execute();

        }
    };

    private static class populating_database extends AsyncTask<Void, Void, Void> {
        private note_data_access_opject note_dao;

        public populating_database(Note_Databse db) {
            note_dao = db.dao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            note_dao.insetr(new Note("Title 1", "Description 1", 1));
            note_dao.insetr(new Note("Title 2", "Description 2", 2));
            note_dao.insetr(new Note("Title 3", "Description 3", 3));
            note_dao.insetr(new Note("Title 4", "Description 4", 4));
            note_dao.insetr(new Note("Title 4", "Description 4", 4));
            note_dao.insetr(new Note("Title 4", "Description 4", 4));


            return null;
        }
    }


}
