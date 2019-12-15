package com.example.libusage.db;

import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.andylib.apicall.ApiCallback;
import com.andylib.apicall.CustomApiCall;
import com.andylib.util.CommonConfig;
import com.example.libusage.R;
import com.libusage.GetUser.GetUserReq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LocalDbActivity extends AppCompatActivity {

    private String TABLE_NAME = "TestTable";
    private DatabaseHelper databaseHelper;
    private int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_db);
        databaseHelper = new DatabaseHelper(this);
    }

    public void click(View v) {
        switch (v.getId()) {
            case R.id.btnCreateTable:
                tableCreate();
                getTableNameFromDb();
                break;
            case R.id.btnInsert:
                insertDataInDb();
                break;
            case R.id.btnEdit:
                editDataInDatabase();
                break;
            case R.id.btnGetAll:
                getDataFromTable();
                break;
            case R.id.btnGetSelected:
                getSelectedDataFromTable();
                break;
            case R.id.btnDelete:
                deleteDataFromDatabase();
                break;
            case R.id.btnApi:
                apiCall();
                break;
        }
    }

    /* this function create table in local database */
    private void tableCreate() {
        ArrayList<String> columnName = new ArrayList<>();
        columnName.add("_id");
        columnName.add("FName");
        columnName.add("LName");

        ArrayList<String> columnType = new ArrayList<>();
        columnType.add("TEXT");
        columnType.add("TEXT");
        columnType.add("TEXT");

        databaseHelper.creatTbl(TABLE_NAME, columnName, columnType);
    }

    /* this function get all table name from created database */
    public void getTableNameFromDb() {
        ArrayList<String> s = databaseHelper.getAllTableName();
        if (s != null) {
            for (int i = 0; i < s.size(); i++) {
                Log.d("dbName", "localDbUsage: table : " + s.get(i));
            }
        }
    }

    /* this function insert data in local database */
    private void insertDataInDb() {
        ArrayList<String> columnName = new ArrayList<>();
        columnName.add("_id");
        columnName.add("FName");
        columnName.add("LName");
        ArrayList<String> columnValue = new ArrayList<>();
        columnValue.add(count + "");
        columnValue.add("Abc");
        columnValue.add("Def");
        databaseHelper.insert(TABLE_NAME, columnName, columnValue);
        count++;
    }

    /* this function get all data from given local database */
    private void getDataFromTable() {
        ArrayList<String> columnName = new ArrayList<>();
        columnName.add("_id");
        columnName.add("FName");
        columnName.add("LName");
        Cursor cursor = databaseHelper.getWhere(TABLE_NAME, columnName, null, null, null);
        ArrayList<String> idList = new ArrayList<>();
        ArrayList<String> nameList = new ArrayList<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    idList.add(cursor.getString(cursor.getColumnIndex("_id")));
                    nameList.add(cursor.getString(cursor.getColumnIndex("FName")));
                } while (cursor.moveToNext());
            }
        }

        if (idList.size() > 0) {
            for (int i = 0; i < idList.size(); i++) {
                Log.d("localDb", "localDbUsage: id : " + idList.get(i));
                Log.d("localDb", "localDbUsage: name : " + nameList.get(i));
            }
        }

    }

    /* this function get data from local database based on selection */
    private void getSelectedDataFromTable() {
        ArrayList<String> columnName = new ArrayList<>();
        columnName.add("_id");
        columnName.add("FName");
        columnName.add("LName");
        String select = "_id=?";
        ArrayList<String> selArgs = new ArrayList<>();
        selArgs.add("1"); // string which you want to check and get from db
        Cursor cursor = databaseHelper.getWhere(TABLE_NAME, columnName, select, selArgs, null);
        ArrayList<String> idList = new ArrayList<>();
        ArrayList<String> nameList = new ArrayList<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    idList.add(cursor.getString(cursor.getColumnIndex("_id")));
                    nameList.add(cursor.getString(cursor.getColumnIndex("FName")));
                } while (cursor.moveToNext());
            }
        }

        if (idList.size() > 0) {
            for (int i = 0; i < idList.size(); i++) {
                Log.d("localDb", "localDbUsage: selected id : " + idList.get(i));
                Log.d("localDb", "localDbUsage: selected name : " + nameList.get(i));
            }
        }
    }

    /* this function edit data in database */
    private void editDataInDatabase() {
        String where = "_id=?";
        ArrayList<String> colName = new ArrayList<>();
        colName.add("FName");
        colName.add("LName");
        ArrayList<String> columnValue = new ArrayList<>();
        columnValue.add("KD");
        columnValue.add("Prajapati");
        ArrayList<String> selArgs = new ArrayList<>();
        selArgs.add("1");
        databaseHelper.update(TABLE_NAME, colName, columnValue, where, selArgs);
    }

    /* this function edit data in database */
    private void deleteDataFromDatabase() {
        String where = "_id=?";
        ArrayList<String> selArgs = new ArrayList<>();
        selArgs.add("1");
        /* this is for delete selected records */
        databaseHelper.delete(TABLE_NAME, where,  selArgs);
        /* this is for delete all records */
        databaseHelper.delete(TABLE_NAME, null, null);
    }

    private void apiCall() {
//        CommonConfig.WsPrefix = "http://1.22.161.26:3131/baits/Api/";
        CommonConfig.WsPrefix = "https://api.randomuser.me/";
        GetUserReq req = new GetUserReq();
        new CustomApiCall(LocalDbActivity.this,
                new Object(),
//                new String[]{"postFix url", "dbTableName(if need to create)", "method type(Get/Post/Put/Delete)"},
                new String[]{"", "", CommonConfig.WsMethodType.GET},
                new HashMap<>(),
                CommonConfig.serviceCallFrom.GENERAL_WS_CALL,
                new ApiCallback() {
                    @Override
                    public void success(String responseData) {
                        Toast.makeText(LocalDbActivity.this, "Success Res : " + responseData, Toast.LENGTH_SHORT).show();
                        Log.d("apiCallRes", "success: " + responseData);
                        // YourModelClass yourModelClass = new Gson().fromJson(responseData, YourModelClass.class);
                    }

                    @Override
                    public void failure(String responseData) {
                        Toast.makeText(LocalDbActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public static Map<String, String> getAllHeaders() {
        //Header
        Map<String, String> mapHeader = new HashMap<>();
//        mapHeader.put("Content-Type", "application/json");
//        mapHeader.put("Appsecret", "PYKuBOdOsne9oJyhKmLt6HDt8Mwt62I5CSS");
        return mapHeader;
    }

    /* header param passed like this */
    public static Map<String, String> getAllHeader() {
        //Header
        Map<String, String> mapHeader = new HashMap<>();
        mapHeader.put("Content-Type", "application/json");
        mapHeader.put("Appsecret", "PYKuBOdOsne9oJyhKmLt6HDt8Mwt62I5CSS");
        return mapHeader;
    }
}