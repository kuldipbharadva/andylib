package com.example.libusage.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DatabaseHelper {


    private MySQLiteHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;


    public DatabaseHelper(Context context) {
        dbHelper = new MySQLiteHelper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }


    public void openDatabase() throws SQLException {
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    public void closeDatabase() {
        dbHelper.close();
    }

    public void creatTbl(String tableName,ArrayList<String> columnsNames, ArrayList columnType){
        String prepareQuery = "";
        for (int i = 0; i < columnsNames.size(); i++) {
            prepareQuery += columnsNames.get(i) + " "+columnType.get(i)+", ";
        }
        String createTbl = "CREATE TABLE IF NOT EXISTS " + tableName + " ("+prepareQuery.substring(0,prepareQuery.length()-2)+");";
        sqLiteDatabase.execSQL(createTbl);

    }

    //this method returns the id of the newly created row and returns -1 on insert none record
    //insert() of sqlite returns inserted record id
    public long insert(String tableName, ArrayList<String> columnsNames, ArrayList columnValues) {
        try {
            if (!sqLiteDatabase.isOpen())
                openDatabase();
            long val = sqLiteDatabase.insert(tableName, null, getContentValues(columnsNames, columnValues));
            closeDatabase();
            return val;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    //    String[] args = new String[] { "UserMasterId", "RoldeId" };
    //    String where = AssetDatabaseOpenHelper.COL_USER_ROLE_PRIVILEGE_UserMasterId + " = ? and " + AssetDatabaseOpenHelper.COL_USER_ROLE_PRIVILEGE_MasterId + " = ? ";
    //update() of sqlite returns updated record id
    public boolean update(String tableName, ArrayList<String> columnsNames, ArrayList columnValues, String where, ArrayList selectionArgs) {
        try {
            if (!sqLiteDatabase.isOpen())
                openDatabase();
            boolean val = sqLiteDatabase.update(tableName, getContentValues(columnsNames, columnValues), where, getStringArgs(selectionArgs)) != -1 ? true : false;
            closeDatabase();
            return val;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //to delete particular records from given table
    //delete() of sqlite returns number of deleted records
    public boolean delete(String tableName, String where, ArrayList args) {
        try {
            if (!sqLiteDatabase.isOpen())
                openDatabase();
            boolean val = sqLiteDatabase.delete(tableName, where, getStringArgs(args)) > 0 ? true : false;
            closeDatabase();
            return val;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    //http://stackoverflow.com/questions/18013912/selectionargs-in-sqlitequerybuilder-doesnt-work-with-integer-values-in-columns
    //User = CAST(? as INTEGER) for int
    //    String[] args = new String[] { "UserMasterId", "RoldeId" };
    //    String selection = AssetDatabaseOpenHelper.COL_USER_ROLE_PRIVILEGE_UserMasterId + " = ? and " + AssetDatabaseOpenHelper.COL_USER_ROLE_PRIVILEGE_MasterId + " = ? ";
    //to get records from table
    //please maintain/handle null pointer exception when use getWhere()
    public Cursor getWhere(String tableName, ArrayList<String> columns, String selection, ArrayList args, String order) {
        try {
            if (!sqLiteDatabase.isOpen())
                openDatabase();
            Cursor cursor = sqLiteDatabase.query(tableName, getStringArgs(columns), selection, getStringArgs(args), null, null, order);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                return cursor;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ContentValues getContentValues(ArrayList<String> columnsNames, ArrayList columnsValues) {
        ContentValues values = new ContentValues();
        for (int i = 0; i < columnsNames.size(); i++) {
            if (columnsValues.get(i) instanceof String)
                values.put(columnsNames.get(i), (String) columnsValues.get(i));
            else if (columnsValues.get(i) instanceof Integer) {
                values.put(columnsNames.get(i), (Integer) columnsValues.get(i));
            } else if (columnsValues.get(i) instanceof Long) {
                values.put(columnsNames.get(i), (Long) columnsValues.get(i));
            } else if (columnsValues.get(i) instanceof Float) {
                values.put(columnsNames.get(i), (Float) columnsValues.get(i));
            } else if (columnsValues.get(i) instanceof Boolean) {
                //Here cast boolean and save its value to int 1 for true and 0 for false
                values.put(columnsNames.get(i), (Boolean) columnsValues.get(i) ? 1 : 0);
            }
        }
        return values;
    }

    public static String[] getStringArgs(ArrayList columnsValues) {
        String[] stringArray = null;

        if (columnsValues != null) {
            stringArray = new String[columnsValues.size()];

            for (int i = 0; i < columnsValues.size(); i++) {
                stringArray[i] = String.valueOf(columnsValues.get(i));
            }

        }

        return stringArray;
    }

    public void truncateTable(String tableName) {
        if (!sqLiteDatabase.isOpen())
            openDatabase();
        if (rowExists(tableName)) {
            sqLiteDatabase.delete(tableName, null, null);
            closeDatabase(); // Closing database connection
        }
    }

    public int getTotalRows(String tableName, String selection, ArrayList args) {
        if (!sqLiteDatabase.isOpen())
            openDatabase();
        return (int) DatabaseUtils.queryNumEntries(sqLiteDatabase, tableName,
                selection, getStringArgs(args));

    }

    public boolean rowExists(String tableName, String selection, ArrayList args) {
        if (!sqLiteDatabase.isOpen())
            openDatabase();
        return DatabaseUtils.queryNumEntries(sqLiteDatabase, tableName, selection, getStringArgs(args)) > 0;
    }

    public boolean rowExists(String tableName) {
        if (!sqLiteDatabase.isOpen())
            openDatabase();
        return DatabaseUtils.queryNumEntries(sqLiteDatabase, tableName) > 0;
    }

    public int getTotalRows(String tableName) {
        if (!sqLiteDatabase.isOpen())
            openDatabase();
        return (int) DatabaseUtils.queryNumEntries(sqLiteDatabase, tableName);
    }

    public ArrayList<String> getAllTableName() {
        Cursor c = sqLiteDatabase.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        ArrayList<String> tName = new ArrayList<>();
        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {
                tName.add(c.getString(0));
                c.moveToNext();
            }
        }
        return tName;
    }
}
