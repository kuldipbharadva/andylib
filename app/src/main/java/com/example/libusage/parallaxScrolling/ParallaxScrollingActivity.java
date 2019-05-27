package com.example.libusage.parallaxScrolling;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.libusage.R;

public class ParallaxScrollingActivity extends AppCompatActivity {

    private String TAG = "parallax";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parallax_scrolling);
        initBasic();
    }

    private void initBasic() {
        ImageView ivBack = findViewById(R.id.ivBack);
        AppBarLayout barLayout = findViewById(R.id.appBarLayout);
        barLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true;
                    ivBack.setVisibility(View.VISIBLE);
                    Log.d(TAG, "onOffsetChanged: true");
                } else if (isShow) {
                    isShow = false;
                    ivBack.setVisibility(View.GONE);
                    Log.d(TAG, "onOffsetChanged: false");
                }
            }
        });
    }
}
