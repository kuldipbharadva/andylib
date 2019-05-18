package com.example.libusage.fdb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import com.example.libusage.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class FdbActivity extends AppCompatActivity {

    private EditText etEditText;
    private Button btnAdd;
    private RecyclerView rvList;
    private DatabaseReference myDbRef;
    private ArrayList<FirebaseModel> stringArrayList;
    private FirebaseModel firebaseModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fdb);
        etEditText = findViewById(R.id.etEditText);
        btnAdd = findViewById(R.id.btnAdd);
        rvList = findViewById(R.id.rvList);
        init();
    }

    private void init() {
        FirebaseApp.initializeApp(FdbActivity.this);
        myDbRef = FirebaseDatabase.getInstance().getReference("FirebaseDb");
        firebaseModel = new FirebaseModel();
        btnAdd.setOnClickListener(v -> {
            if (!etEditText.getText().toString().equalsIgnoreCase("")) {
                String id = myDbRef.push().getKey();
                FirebaseModel firebaseModel = new FirebaseModel(etEditText.getText().toString());
                myDbRef.child(id).setValue(firebaseModel); //  add record or edit record if you specify specific key
//                myDbRef.child(id).removeValue(); // this line remove record from db (give key's record)
                etEditText.setText("");
                Log.d("firebaseDb", "onDataChange: id : " + id);
            } else {
                Toast.makeText(this, "Please enter value", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        myDbRef = FirebaseDatabase.getInstance().getReference("FirebaseDb");
        myDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                stringArrayList = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Log.d("firebaseDb", "onDataChange: key : " + postSnapshot.getKey());
                    Log.d("firebaseDb", "onDataChange: ref : " + postSnapshot.getRef());
                    //getting val
                    FirebaseModel s = postSnapshot.getValue(FirebaseModel.class);
                    //adding val to the list
                    if (s != null) {
                        stringArrayList.add(s);
                        firebaseModel.setVal(s.getVal());
                        Log.d("firebaseDb", "onDataChange: val : " + s.getVal());
                        Log.d("firebaseDb", "onDataChange: size : " + stringArrayList.size());
                        setList();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("firebaseDb", "onCancelled: error : " + databaseError);
            }
        });

        new FirebaseModel();
    }

    private void setList() {
        FdbAdapter fdbAdapter = new FdbAdapter(FdbActivity.this, stringArrayList);
        rvList.setAdapter(fdbAdapter);
        rvList.setLayoutManager(new LinearLayoutManager(FdbActivity.this));
        rvList.setHasFixedSize(true);
        fdbAdapter.OnItemClick(position -> {
            myDbRef.child(Objects.requireNonNull(myDbRef.push().getKey())).removeValue();
        });
        rx();
    }

    private void rx() {
        Observable<FirebaseModel> stringObservable = Observable.create(e -> {
//            Log.d("rxAndroid", "rx: v : " + firebaseModel.getVal());
//            firebaseModel.setVal("Testing");
            e.onNext(firebaseModel);
            e.onError(new Exception());
        });

        Observer<FirebaseModel> firebaseModelObserver = new Observer<FirebaseModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(FirebaseModel firebaseModel) {
                Log.d("rxAndroid", "onNext: val : " + firebaseModel.getVal());
            }

            @Override
            public void onError(Throwable e) {
                Log.d("rxAndroid", "onError: val : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d("rxAndroid", "onComplete: Done");
            }
        };

        stringObservable.subscribe(firebaseModelObserver);
    }
}
