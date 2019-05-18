package com.example.libusage.pipMode;

import android.app.PictureInPictureParams;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Rational;
import android.view.Display;
import android.view.View;
import android.widget.Button;

import com.example.libusage.R;


public class PiPModeActivity extends AppCompatActivity {

    private Button btnPipMode;
    private PictureInPictureParams.Builder pictureInPictureParamsBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pip_mode);
        initBasic();
        initEventListener();
    }

    private void initBasic() {
        btnPipMode = findViewById(R.id.btnPipMode);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            pictureInPictureParamsBuilder =
                    new PictureInPictureParams.Builder();
        }
    }

    /* this function handle click event of control */
    private void initEventListener() {
        btnPipMode.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                pictureInPictureMode();
            }
        });
    }

    /* this function open pip mode */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void pictureInPictureMode() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        Rational aspectRatio = new Rational(width, height);
        pictureInPictureParamsBuilder.setAspectRatio(aspectRatio).build();
        enterPictureInPictureMode(pictureInPictureParamsBuilder.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onUserLeaveHint() {
        if (!isInPictureInPictureMode()) {
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;
            Rational aspectRatio = new Rational(width, height);
            pictureInPictureParamsBuilder.setAspectRatio(aspectRatio).build();
            enterPictureInPictureMode(pictureInPictureParamsBuilder.build());
        }
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
        if (isInPictureInPictureMode) {
            btnPipMode.setVisibility(View.GONE);
        } else {
            btnPipMode.setVisibility(View.VISIBLE);
        }
    }
}