package com.example.libusage.qrCode;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.libusage.R;

public class QrCodeGeneratorActivity extends AppCompatActivity {

    private EditText etQrContent;
    private ImageView ivQrCode;
    private String content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_generator);
        init();
    }

    private void init() {
        etQrContent = findViewById(R.id.etQrContent);
        ivQrCode = findViewById(R.id.ivQrCode);

        content = "Hello Qr Generator"; // Whatever you need to encode in the QR code

        findViewById(R.id.btnGenerate).setOnClickListener(v -> {
            if (!etQrContent.getText().toString().equalsIgnoreCase("")) {
                content = etQrContent.getText().toString();
                if (QrGenerator.generateQrCodeBitmapImage(content) != null) {
                    ivQrCode.setImageBitmap(QrGenerator.generateQrCodeBitmapImage(content));
                }
            }
        });
    }
}
