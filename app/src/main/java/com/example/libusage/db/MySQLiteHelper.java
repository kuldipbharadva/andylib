package com.example.libusage.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MySQLiteHelper extends SQLiteOpenHelper {

    public static String DB_NAME = "WSData.sqlite";

    //APPLICATION SERVICE TABLE
    public static String TBL_NAME = "WSData";
    public static final String COL_TABLE_NAME = "tblName";
    public static final String COL_DATA = "dataJSON";
    public static final String COL_DATE = "updatedDate";

    //API TABLE
    public static String TBL_NAME_API = "APIData";
    public static final String COL_CLASS_NAME = "className";
    public static final String COL_API_DATA = "APIData";


    public
    MySQLiteHelper(Context context) {
        super(context, DB_NAME, null, 1);
//        super(context, DB_NAME, null, Utility.getDataBaseVersion(context));
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTbl = "CREATE TABLE IF NOT EXISTS " + TBL_NAME + " (" + COL_TABLE_NAME + " TEXT, " + COL_DATA + " TEXT, " + COL_DATE + " TEXT);";
        db.execSQL(createTbl);
        String createTblAPI = "CREATE TABLE IF NOT EXISTS " + TBL_NAME_API + " (" + COL_CLASS_NAME + " TEXT, " + COL_API_DATA + " TEXT);";
        db.execSQL(createTblAPI);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TBL_NAME);
            onCreate(db);
        }
    }
}
