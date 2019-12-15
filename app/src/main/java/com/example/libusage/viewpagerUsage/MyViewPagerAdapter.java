package com.example.libusage.viewpagerUsage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments = new ArrayList<>();
    private List<String> tabTitles = new ArrayList<>();

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
}