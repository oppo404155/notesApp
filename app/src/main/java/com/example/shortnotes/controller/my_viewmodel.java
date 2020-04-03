package com.example.shortnotes.controller;

import android.app.Application;

import com.example.shortnotes.model.Note;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class my_viewmodel extends AndroidViewModel {

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

