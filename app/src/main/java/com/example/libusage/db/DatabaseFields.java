package com.example.libusage.db;

import java.util.ArrayList;

public class DatabaseFields {

//    public static String DB_NAME = Environment.getExternalStorageDirectory()
//            + File.separator + "TruckerLinkTier"
//            + File.separator + "TruckerLinkTier.sqlite";

    public static String FIELD_TYPE_TEXT = "TEXT";
    public static String FIELD_TYPE_INT = "INTEGER";

    /* Your Table's Var */
    public static String TBL_NAME_TEST = "TestTable";
    public static final String COL_ID = "id";
    public static final String COL_USER_NAME = "userName";
    public static final String COL_EMAIL = "email";

    public static ArrayList<String> tableTestColumn() {
        ArrayList<String> columnName = new ArrayList<>();
        columnName.add(COL_ID);
        columnName.add(COL_USER_NAME);
        columnName.add(COL_EMAIL);
        return columnName;
    }

    public static ArrayList<String> tableTruckRunType() {
        ArrayList<String> columnType = new ArrayList<>();
        columnType.add(FIELD_TYPE_TEXT);
        columnType.add(FIELD_TYPE_TEXT);
        columnType.add(FIELD_TYPE_TEXT);
        return columnType;
    }
}