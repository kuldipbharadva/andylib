package com.example.libusage.viewPagerCustomTab;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.libusage.R;
import com.example.libusage.drawerUse.HomeFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCustomTabActivity extends AppCompatActivity {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private Context mContext;
    private MyViewPagerAdapter myViewPagerAdapter;
    private String[] tabTitle = {"One", "Two", "Three"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_custom_tab);
        ButterKnife.bind(this);
        initBasicTask();
    }

    private void initBasicTask() {
        mContext = MyCustomTabActivity.this;
        setUpViewPager();
        //        setCustomCurrentTab(0);
        setupTabView(0);
    }

    /* this function set custom layout of selected current tab */
    private void setCustomCurrentTab(int position) {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            assert tab != null;
            if (i == position) {
                tab.setCustomView(null);
                tab.setCustomView(myViewPagerAdapter.getTabView(mContext, position));
            } else {
                tab.setCustomView(null);
            }
        }
    }

    /* this function set custom layout of selected current tab */
    public void setupTabView(int position) {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            Objects.requireNonNull(tabLayout.getTabAt(i)).setCustomView(null);
            Objects.requireNonNull(tabLayout.getTabAt(i)).setCustomView(R.layout.tab_custom_item);
            TextView tvTabItem = Objects.requireNonNull(Objects.requireNonNull(tabLayout.getTabAt(i)).getCustomView()).findViewById(R.id.tvTabItem);
            tvTabItem.setText(tabTitle[i]);
            if (position == i) {
                tvTabItem.setTextColor(getResources().getColor(R.color.colorAccent));
                tvTabItem.setBackground(getResources().getDrawable(R.drawable.bg_radius_corner));
            }/* else {
                tvTabItem.setTextColor(getResources().getColor(R.color.colorBlack));
                tvTabItem.setBackground(getResources().getDrawable(R.drawable.bg_reg_simple));
            }*/
        }
    }

    private void setUpViewPager() {
        myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        myViewPagerAdapter.addFragment(new HomeFragment(), tabTitle[0]);
        myViewPagerAdapter.addFragment(new HomeFragment(), tabTitle[1]);
        myViewPagerAdapter.addFragment(new HomeFragment(), tabTitle[2]);
        viewPager.setAdapter(myViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
//                setCustomCurrentTab(position);
                setupTabView(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
}

/* Reference Link :: https://stackoverflow.com/questions/40896907/can-a-custom-view-be-used-as-a-tabitem/40897198 */