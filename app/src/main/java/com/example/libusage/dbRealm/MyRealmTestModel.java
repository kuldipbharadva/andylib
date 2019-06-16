package com.example.libusage.dbRealm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class MyRealmTestModel extends RealmObject {

    @PrimaryKey
    private int id;
    @Required
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
 
    public void setTitle(final String title) {
        this.title = title;
    }
 
}