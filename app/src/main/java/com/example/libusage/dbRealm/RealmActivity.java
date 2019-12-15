package com.example.libusage.dbRealm;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.andylib.preferences.MySharedPreference;
import com.example.libusage.R;

import java.util.ArrayList;

import io.realm.Realm;

@SuppressWarnings("ALL")
public class RealmActivity extends AppCompatActivity {

    private Realm mRealm;
    private Context mContext;
    private EditText etTitle, etUpdateTitle;
    private RecyclerView rvList;
    private String myPref = "MyPreference";
    private String myPrefKey = "RealmId";
    private int realmId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realm);
        initBasic();
    }

    private void initBasic() {
        mContext = RealmActivity.this;
        mRealm = RealmHelper.getRealmInstance(mContext);
        etTitle = findViewById(R.id.etTitle);
        etUpdateTitle = findViewById(R.id.etUpdateTitle);
        rvList = findViewById(R.id.rvList);
        findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidateToAdd()) addRecord();
            }
        });
        findViewById(R.id.btnUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidateToUpdate()) updateRecord();
            }
        });
        setRecyclerView();
    }

    private void getPreferenceIdForRealm() {
        realmId = (int) MySharedPreference.getPreference(mContext, myPref, myPrefKey, 1);
    }

    private void setPreferenceIdForRealm(int id) {
        MySharedPreference.setPreference(mContext, myPref, myPrefKey, id);
    }

    public void clearModel(View view) {
        cleareRealmModelUsage();
    }

    /* this function get title from EditText which entered by user. */
    private String getTrimmedTitle() {
        return etTitle.getText().toString().trim();
    }

    /* this function add record from RealM db */
    private void addRecord() {
        getPreferenceIdForRealm();
        mRealm.beginTransaction();
        MyRealmTestModel myRealmTestModel = mRealm.createObject(MyRealmTestModel.class);
        myRealmTestModel.setId(realmId);
        myRealmTestModel.setTitle(getTrimmedTitle());
        mRealm.commitTransaction();
        etTitle.setText("");
        etUpdateTitle.setText("");
        setPreferenceIdForRealm(realmId + 1);
    }

    /* this function update record in RealM db */
    private void updateRecord() {
        MyRealmTestModel myRealmTestModel = mRealm.where(MyRealmTestModel.class).equalTo("title", getTrimmedTitle()).findFirst();
        if (myRealmTestModel != null) {
            mRealm.beginTransaction();
            myRealmTestModel.setTitle(etUpdateTitle.getText().toString().trim());
            mRealm.commitTransaction();
            etUpdateTitle.setText("");
            etTitle.setText("");
            etTitle.requestFocus();
        }
    }

    /* this function check validation before add record */
    private boolean isValidateToAdd() {
        String title = etTitle.getText().toString().trim();
        if (title.equalsIgnoreCase("")) {
            etTitle.setError("Please Enter Title!");
            etTitle.requestFocus();
            return false;
        }
        /* usage of isExist method from RealmHelper class */
        if (RealmHelper.isExist(MyRealmTestModel.class, "title", title, null)) {
            Toast.makeText(mContext, "Already Exist!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /* this function check validation before update record */
    private boolean isValidateToUpdate() {
        String updateTitle = etUpdateTitle.getText().toString().trim();
        if (updateTitle.equalsIgnoreCase("")) {
            etUpdateTitle.setError("Please Enter Update Title!");
            etUpdateTitle.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }

    /* this function set RealM db list into recycler view */
    private void setRecyclerView() {
        /* usage of get all records from realm model */
        final RealmTestingAdapter myListAdapter = new RealmTestingAdapter(RealmHelper.getAllRecords(MyRealmTestModel.class));
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        rvList.setAdapter(myListAdapter);
        rvList.setHasFixedSize(true);
        myListAdapter.setOnItemClick(new RealmTestingAdapter.OnItemClickListener() {
            @Override
            public void onClickItem(Object o) {
                MyRealmTestModel myRealmTestModel = (MyRealmTestModel) o;
                /* here is usage of delete single record from realm db */
                RealmHelper.deleteRealm(MyRealmTestModel.class, "title", myRealmTestModel.getTitle(), null);
            }
        });
    }

    /* this function show how to use RealmHelper's common mehtod for check
       record isExist using multiple where condition checking */
    private void isExistRecordUsage() {
        ArrayList<String> strings = new ArrayList<>();
        ArrayList<Object> val = new ArrayList<>();
        strings.add("id");
        strings.add("title");
        val.add(1);
        val.add("Kd");
        boolean isExist = RealmHelper.isExist(MyRealmTestModel.class, strings, val, null);
        Toast.makeText(mContext, "" + isExist, Toast.LENGTH_SHORT).show();
    }

    /* this function show how to clear model using RealmHelper class method */
    private void cleareRealmModelUsage() {
        RealmHelper.deleteRealm(MyRealmTestModel.class);
    }
}
