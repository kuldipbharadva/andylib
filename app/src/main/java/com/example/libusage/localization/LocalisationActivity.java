package com.example.libusage.localization;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.andylib.preferences.MySharedPreference;
import com.example.libusage.R;

import java.util.Locale;

public class LocalisationActivity extends AppCompatActivity {

    private TextView tvName, tvCity, tvAddress, tvMobile;
    private Button btnEng, btnSpain, btnArabic;

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
        btnEng = findViewById(R.id.btnEng);
        btnSpain = findViewById(R.id.btnSpain);
        btnArabic = findViewById(R.id.btnArabic);
        btnEng.setOnClickListener(v -> {
            setLocale("en");
            /* if there is no activity in back stack then no need to call reopenActivity */
            reopenActivity();
        });
        btnSpain.setOnClickListener(v -> {
            setLocale("es");
            reopenActivity();
        });
        btnArabic.setOnClickListener(v -> {
            setLocale("ar");
            reopenActivity();
        });
        findViewById(R.id.btnNext).setOnClickListener(v ->
                startActivity(new Intent(LocalisationActivity.this, LocalisationTestingActivity.class))
        );
    }

    /* this function set given locale language and set in preference */
    public void setLocale(String lang) {
        MySharedPreference.setPreference(LocalisationActivity.this, "prefLang", "lang", lang);
        Locale myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.setLocale(myLocale);
        conf.setLayoutDirection(myLocale);
        res.updateConfiguration(conf, dm);
        onConfigurationChanged(conf);
    }

    /* this function reopen current activity */
    private void reopenActivity() {
        try {
            startActivity(getIntent());
            overridePendingTransition(0, 0);
            finish();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    /* this function get lang from pref and set in locale */
    private void setLocaleFromPref() {
        String myLang = (String) MySharedPreference.getPreference(LocalisationActivity.this, "prefLang", "lang", "en");
        setLocale(myLang);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("lifeCycle", "onResume: ");
        setLocaleFromPref();
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