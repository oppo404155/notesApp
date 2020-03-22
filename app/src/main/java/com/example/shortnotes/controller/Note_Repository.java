package com.example.shortnotes.controller;

import android.app.Application;
import android.os.AsyncTask;

import com.example.shortnotes.model.Note;
import com.example.shortnotes.model.Note_Databse;

import java.util.List;

import androidx.lifecycle.LiveData;

public class Note_Repository {
    private note_data_access_opject note_dao;
    private LiveData<List<Note>> allnotes;

    public Note_Repository(Application application) {
        Note_Databse note_databse = Note_Databse.getInstance(application);
        note_dao = note_databse.dao();

        allnotes = note_dao.get_all_notes();

    }

    public void insert(Note note) {
        new insert_async(note_dao).execute(note);


    }

    public void updaet(Note note) {
        new uppdate_async(note_dao).execute(note);

    }

    public void delete(Note note) {
        new delete_async(note_dao).execute(note);

    }

    public LiveData<List<Note>> getAllnotes() {

        return allnotes;
    }

    public void delete_all_notes() {
        new delete_async.deleteall_async(note_dao).execute();

    }

    private static class insert_async extends AsyncTask<Note, Void, Void> {
        private note_data_access_opject note_dao;

        public insert_async(note_data_access_opject note_dao) {
            this.note_dao = note_dao;
        }

        @Override
        protected Void doInBackground(Note... notes) {

            note_dao.insetr(notes[0]);

            return null;
        }
    }

    private static class uppdate_async extends AsyncTask<Note, Void, Void> {
        private note_data_access_opject note_dao;

        public uppdate_async(note_data_access_opject note_dao) {
            this.note_dao = note_dao;
        }

        @Override
        protected Void doInBackground(Note... notes) {

            note_dao.update(notes[0]);

            return null;
        }
    }

    private static class delete_async extends AsyncTask<Note, Void, Void> {
        private note_data_access_opject note_dao;

        public delete_async(note_data_access_opject note_dao) {
            this.note_dao = note_dao;
        }

        @Override
        protected Void doInBackground(Note... notes) {

            note_dao.delete(notes[0]);

            return null;
        }

        private static class deleteall_async extends AsyncTask<Void, Void, Void> {
            private note_data_access_opject note_dao;

            public deleteall_async(note_data_access_opject note_dao) {
                this.note_dao = note_dao;
            }

            @Override
            protected Void doInBackground(Void... voids) {

                note_dao.delete_all_tables();

                return null;
            }
        }


    }

}
