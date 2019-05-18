package com.example.libusage.qrCode;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.andylib.dialog.LocationDialog;
import com.example.libusage.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class QrCodeScannerActivity extends AppCompatActivity {

    private TextView tvQrScanContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scanner);
        init();
    }

    private void init() {
        tvQrScanContent = findViewById(R.id.tvQrScanContent);
        findViewById(R.id.btnScan).setOnClickListener(v -> {
            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
//            integrator.setPrompt("Scan");
            integrator.setCameraId(0);  // Use a specific camera of the device
            integrator.setBeepEnabled(false);
            integrator.setBarcodeImageEnabled(true);
            integrator.initiateScan();
        });
//        LocationDialog locationDialog = new LocationDialog(QrCodeScannerActivity.this);
//        locationDialog.mEnableGps();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                tvQrScanContent.setText(result.getContents() + "\n" + result.getFormatName());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
}
