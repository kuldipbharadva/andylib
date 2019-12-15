package com.example.libusage.pagination;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import android.view.View;

import com.example.libusage.R;

import java.util.ArrayList;
import java.util.Objects;

public class PaginationOneActivity extends AppCompatActivity {

    private RecyclerView rvList;
    private boolean isLoading = false;
    private String offSet;
    private PaginationAdapter adapter;
    private ArrayList<String> mList;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagination);
        initBasic();
    }

    private void initBasic() {
        rvList = findViewById(R.id.rvList);
        mList = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(PaginationOneActivity.this);
        adapter = new PaginationAdapter(PaginationOneActivity.this, mList);
        rvList.setLayoutManager(linearLayoutManager);
        rvList.setAdapter(adapter);
        rvList.setHasFixedSize(true);
        ((SimpleItemAnimator) Objects.requireNonNull(rvList.getItemAnimator())).setSupportsChangeAnimations(false);

        rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = linearLayoutManager.getChildCount();
                int totalItemCount = linearLayoutManager.getItemCount();
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

                if (isLoading) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0) {
                        isLoading = false;
                        /* this api called when user scroll listing and
                           check offSet from api response is not -1 and manage response and pagination */
                        apiCallResponseBlock();
                    }
                }
            }
        });

        /* this is first time api calling when user landing on this activity */
        apiCallResponseBlock();
    }

    /* this function called listing api, here is only manage api success block */
    private void apiCallResponseBlock() {
        /* this is api response model filled from response */
        PaginationModel bean = null;

        offSet = bean.getOffSet();
        rvList.setVisibility(View.VISIBLE);
        if (!isLoading) {
            if (mList != null)
                mList.clear();
        }
        mList.addAll(bean.getmEventslist());
        adapter.notifyDataSetChanged();

        if (offSet.equalsIgnoreCase("-1")) {
            isLoading = false;
        } else {
            isLoading = true;
        }
    }
}
