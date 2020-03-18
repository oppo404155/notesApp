package com.example.shortnotes;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface note_data_access_opject {
    @Insert
    void insetr(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("DELETE FROM note_table")
    void delete_all_tables();

    @Query("SELECT* FROM Note_table ORDER BY priority DESC")
    LiveData<List<Note>> get_all_notes();


}
