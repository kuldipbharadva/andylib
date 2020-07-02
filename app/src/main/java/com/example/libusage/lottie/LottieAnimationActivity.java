package com.example.libusage.lottie;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.libusage.R;

public class LottieAnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottie_animation);
    }
}

/*
* add dependency : implementation "com.airbnb.android:lottie:2.7.0"
* download lottie animation json file and put it in assets folder.
* set xml layout and set json file : i.e,
   <com.airbnb.lottie.LottieAnimationView
        android:id="@+id/lottieAnim"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:lottie_autoPlay="true"
        app:lottie_fileName="empty_list.json"
        app:lottie_loop="true" />
*/