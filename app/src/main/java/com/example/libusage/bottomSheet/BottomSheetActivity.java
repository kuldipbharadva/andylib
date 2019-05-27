package com.example.libusage.bottomSheet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.libusage.R;
import com.flipboard.bottomsheet.BottomSheetLayout;

public class BottomSheetActivity extends AppCompatActivity {

    private BottomSheetLayout bottomSheet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_case);
        initBasic();
    }

    private void initBasic() {
        bottomSheet = findViewById(R.id.bottomSheet);
        /* this line get root view of layout */
        getWindow().getDecorView().findViewById(android.R.id.content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBottomSheet();
            }
        });
    }

    /* this function open bottom sheet and you can give on click of open options from bottom sheet. */
    private void openBottomSheet() {
        bottomSheet.showWithSheetView(LayoutInflater.from(BottomSheetActivity.this)
                .inflate(R.layout.dialog_open_pic_option, bottomSheet, false));
        findViewById(R.id.tvCamera).setOnClickListener(v -> {
            Toast.makeText(BottomSheetActivity.this, "Camera", Toast.LENGTH_SHORT).show();
            if (bottomSheet != null && bottomSheet.isSheetShowing()) bottomSheet.dismissSheet();
        });
        findViewById(R.id.tvGallery).setOnClickListener(v -> {
            Toast.makeText(BottomSheetActivity.this, "Gallery", Toast.LENGTH_SHORT).show();
            if (bottomSheet != null && bottomSheet.isSheetShowing()) bottomSheet.dismissSheet();
        });
    }
}

/*
 *  For BottomSheet use need to add dependency.
 *  TODO :
 *    implementation 'com.flipboard:bottomsheet-core:1.5.3'
 *
 *  Then just use above code to open bottom sheet and give on click event of your option.
 *  Keep in mind you can give onClick event after bottom sheet open, if you write onClick event before .showWithSheetView
 *  method there might be chance to crash your app.
 *
 *  Todo :
 *    Ref : https://github.com/Flipboard/bottomsheet
 */