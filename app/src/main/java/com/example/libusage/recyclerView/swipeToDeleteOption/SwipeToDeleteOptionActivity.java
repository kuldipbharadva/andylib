package com.example.libusage.recyclerView.swipeToDeleteOption;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libusage.R;
import com.example.libusage.recyclerView.swipeToDelete.ViewBinderHelper;

import java.util.ArrayList;

public class SwipeToDeleteOptionActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<String> stringArrayList = new ArrayList<>();
    private ViewBinderHelper viewBinderHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        recyclerView = findViewById(R.id.recyclerView);
        populateRecyclerView();
    }

    private void populateRecyclerView() {
        viewBinderHelper = new ViewBinderHelper();
        stringArrayList.add("This is Item 1");
        stringArrayList.add("This is Item 2");
        stringArrayList.add("This is Item 3");
        stringArrayList.add("This is Item 4");
        stringArrayList.add("This is Item 5");
        stringArrayList.add("This is Item 6");
        stringArrayList.add("This is Item 7");
        stringArrayList.add("This is Item 8");
        stringArrayList.add("This is Item 9");
        stringArrayList.add("This is Item 10");

        MyAdapterOption mAdapter = new MyAdapterOption(SwipeToDeleteOptionActivity.this, stringArrayList, viewBinderHelper);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(SwipeToDeleteOptionActivity.this));
        recyclerView.setHasFixedSize(true);
    }
}
