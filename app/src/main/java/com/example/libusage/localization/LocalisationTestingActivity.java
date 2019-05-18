package com.example.libusage.localization;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.andylib.preferences.MySharedPreference;
import com.example.libusage.R;

import java.util.Locale;

public class LocalisationTestingActivity extends AppCompatActivity {

    private TextView tvName, tvCity, tvAddress, tvMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localization);
        init();
    }

    private void init() {
        tvName = findViewById(R.id.tvName);
        tvCity = findViewById(R.id.tvCity);
        tvAddress = findViewById(R.id.tvAddress);
        tvMobile = findViewById(R.id.tvMobile);
        findViewById(R.id.btnNext).setVisibility(View.INVISIBLE);
        findViewById(R.id.btnEng).setOnClickListener(v -> {
            setLocale("en");
            reopenActivity();
        });
        findViewById(R.id.btnSpain).setOnClickListener(v -> {
            setLocale("es");
            reopenActivity();
        });
        findViewById(R.id.btnArabic).setOnClickListener(v -> {
            setLocale("ar");
            reopenActivity();
        });
    }

    /* this function set given locale language and set in preference */
    public void setLocale(String lang) {
        try {
            MySharedPreference.setPreference(LocalisationTestingActivity.this, "prefLang", "lang", lang);
            Locale myLocale = new Locale(lang);
            Locale.setDefault(myLocale);
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.setLocale(myLocale);
            conf.setLayoutDirection(myLocale);
            res.updateConfiguration(conf, dm);
            onConfigurationChanged(conf);
        } catch (Exception e) {
            Log.d("localLang", "setLocale: " + e.getLocalizedMessage());
        }
    }

    /* this function reopen current activity */
    private void reopenActivity() {
        startActivity(getIntent());
        overridePendingTransition(0, 0);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String myLang = (String) MySharedPreference.getPreference(LocalisationTestingActivity.this, "prefLang", "lang", "en");
        setLocale(myLang);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("lifeCycle", "onStart: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("lifeCycle", "onPause: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        reopenActivity();
        Log.d("lifeCycle", "onRestart: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("lifeCycle", "onStop: ");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        tvName.setText(getString(R.string.fullName));
        tvCity.setText(getString(R.string.city));
        tvAddress.setText(getString(R.string.address));
        tvMobile.setText(getString(R.string.mobile));
    }
}