package com.example.libusage.viewpagerUsage;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.libusage.R;

public class ViewPagerUsageActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_usage);
        initBasic();
    }

    private void initBasic() {
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        setUpViewPager();
    }

    private void setUpViewPager() {
        myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        myViewPagerAdapter.addFragment(new TabOneFragment(), "TAB ONE");
        myViewPagerAdapter.addFragment(new TabTwoFragment(), "TAB TWO");
        viewPager.setAdapter(myViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    /* this function set custom font to tab */
    private void setCustomFontToTab(TabLayout tabLayout, Context context) {
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    //Put your font in assets folder
                    //assign name of the font here (Must be case sensitive)
                    ((TextView) tabViewChild).setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts1/dejavusanscondensed.otf"));
                    ((TextView) tabViewChild).setTextSize(22);
                }
            }
        }
    }

}
