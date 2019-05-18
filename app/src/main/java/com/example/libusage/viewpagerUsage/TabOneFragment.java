package com.example.libusage.viewpagerUsage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.libusage.R;

public class TabOneFragment extends Fragment {

    private Boolean isStarted = false;
    private Boolean isVisible = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tabs, container, false);
        castingControl(view);
        return view;
    }

    private void castingControl(View view) {
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (isStarted && isVisible) {
            viewDidAppear();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        isStarted = true;
        if (isVisible) {
            viewDidAppear();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        isStarted = false;
        isVisible = false;
    }

    public void viewDidAppear() {
    }
}