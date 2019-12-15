package com.example.libusage.dbRoom;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM " + Constants.TABLE_NAME_NOTE)
    List<NoteModel> getAllNotes();

    @Query("SELECT * FROM " + Constants.TABLE_NAME_NOTE + " WHERE title = :myTitle COLLATE NOCASE")
    boolean isExist(String myTitle);

    /*
     * Insert the object in database
     * @param note, object to be inserted
     */
    @Insert
    void insert(NoteModel note);

    @Insert
    void insertAll(List<NoteModel> noteList);

    /*
     * update the object in database
     * @param note, object to be updated
     */
    @Update
    void update(NoteModel repos);

    /*
     * delete the object from database
     * @param note, object to be deleted
     */
    @Delete
    void delete(NoteModel note);

    /*
     * delete list of objects from database
     * @param note, array of objects to be deleted
     */
    @Delete
    void delete(NoteModel... note);      // NoteModel... is varargs, here note is an array

    @Delete
    void deleteAll(List<NoteModel> noteList);
}