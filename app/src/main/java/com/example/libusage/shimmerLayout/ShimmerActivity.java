package com.example.libusage.shimmerLayout;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libusage.R;
import com.facebook.shimmer.ShimmerFrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShimmerActivity extends AppCompatActivity {

    @BindView(R.id.shimmerLayout)
    ShimmerFrameLayout shimmerLayout;
    @BindView(R.id.rvNotificationList)
    RecyclerView rvNotificationList;
    @BindView(R.id.tvNoData)
    TextView tvNoData;

    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shimmer);
        ButterKnife.bind(this);
        initBasicTask();
    }

    private void initBasicTask() {
        mContext=ShimmerActivity.this;

        shimmerLayout.stopShimmer();
        rvNotificationList.setVisibility(View.GONE);

        //        rvNotificationList.setAdapter(new NotificationListAdapter(mContext));
        rvNotificationList.setLayoutManager(new LinearLayoutManager(mContext));
        rvNotificationList.setHasFixedSize(true);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                shimmerLayout.stopShimmer();
                shimmerLayout.setVisibility(View.GONE);
                rvNotificationList.setVisibility(View.VISIBLE);
            }
        }, 3000);
    }
}
