package com.example.libusage.dbRoom;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = Constants.TABLE_NAME_NOTE)
public class NoteModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int note_id;

    @ColumnInfo(name = "note_content") // column name will be "note_content" instead of "content" in table
    private String content;

    private String title;

    public NoteModel(String title, String note) {
        this.title = title;
        this.content = note;
    }

    public NoteModel(){}

    public int getNote_id() {
        return note_id;
    }

    public void setNote_id(int note_id) {
        this.note_id = note_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}