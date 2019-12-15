package com.example.libusage.internetChecking;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

import com.example.libusage.MyApplication;

public class InternetCheckingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initNetChecking();
    }

    private void initNetChecking() {
        if (ConnectivityReceiver.isConnected()) {
            Toast.makeText(InternetCheckingActivity.this, "Connected", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(InternetCheckingActivity.this, "No Network", Toast.LENGTH_SHORT).show();
        }

        MyApplication.getInstance().setConnectivityListener(new ConnectivityReceiver.ConnectivityReceiverListener() {
            @Override
            public void onNetworkConnectionChanged(boolean isConnected) {
                if (isConnected) {
                    Toast.makeText(InternetCheckingActivity.this, "Connected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(InternetCheckingActivity.this, "No Network", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
