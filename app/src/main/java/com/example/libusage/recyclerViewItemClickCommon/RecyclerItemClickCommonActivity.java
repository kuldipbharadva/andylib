package com.example.libusage.recyclerViewItemClickCommon;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libusage.R;


public class RecyclerItemClickCommonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBasicTask();
    }

    private void initBasicTask() {
        RecyclerView recyclerView=null;
        recyclerView.setAdapter(new ItemClickUseAdapter(RecyclerItemClickCommonActivity.this, new OnItemClickInterface() {
            @Override
            public void onItemClick(Context context, int position, int controlId) {

            }
        }));
    }
}
