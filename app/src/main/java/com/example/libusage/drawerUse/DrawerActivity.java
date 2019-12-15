package com.example.libusage.drawerUse;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.libusage.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DrawerActivity extends AppCompatActivity {

    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.ivHamburger)
    ImageView ivHamburger;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        ButterKnife.bind(this);
        initBasicTask();
    }

    private void initBasicTask() {
        loadFragment(new HomeFragment());

        ivHamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            try {
                if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                    drawerLayout.closeDrawer(GravityCompat.END);
                }
            } catch (Exception e) {
                e.getLocalizedMessage();
            }
        } else {
            super.onBackPressed();
        }
    }

    private void closeDrawer() {
        try {
            if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                drawerLayout.closeDrawer(GravityCompat.END);
            }
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }
}
