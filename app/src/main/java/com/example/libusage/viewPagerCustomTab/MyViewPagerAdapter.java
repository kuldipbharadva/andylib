package com.example.libusage.viewPagerCustomTab;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.libusage.R;

import java.util.ArrayList;
import java.util.List;

public class MyViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments=new ArrayList<>();
    private List<String> tabTitles=new ArrayList<>();

    public MyViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments != null ? fragments.size() : 0;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles.get(position);
    }

    public void addFragment(Fragment fragment, String tabTitle) {
        fragments.add(fragment);
        tabTitles.add(tabTitle);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    /* for custom tab */
    public View getTabView(Context context, int position) {
        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
        @SuppressLint("InflateParams") View v = LayoutInflater.from(context).inflate(R.layout.tab_custom_item, null);
        TextView tv =v.findViewById(R.id.tvTabItem);
        tv.setText(tabTitles.get(position));
        tv.setTextColor(context.getResources().getColor(R.color.colorAccent));
        tv.setBackground(context.getResources().getDrawable(R.drawable.bg_radius_corner));
        return v;
    }
}