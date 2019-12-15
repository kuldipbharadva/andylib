package com.example.libusage.showCaseView;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.example.libusage.R;

import me.toptas.fancyshowcase.FancyShowCaseView;

public class ShowCaseViewUsageActivity extends AppCompatActivity {

    private FancyShowCaseView mFancyShowCaseView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_case);
        initBasic();
    }

    private void initBasic() {
        openShowCase(findViewById(R.id.tvShowCase));
    }

    /* this is show case view function which generally used for tutorial purpose. */
    private void openShowCase(View view) {
        mFancyShowCaseView = new FancyShowCaseView.Builder(ShowCaseViewUsageActivity.this)
                .focusOn(view)
                .title("This is Showcase View.")
                .build();
        mFancyShowCaseView.show();
    }

    @Override
    public void onBackPressed() {
        if (mFancyShowCaseView != null && mFancyShowCaseView.isShown()) {
            mFancyShowCaseView.hide();
        } else {
            super.onBackPressed();
        }
    }
}

/*
 *  TODO
 *    Reference :~  https://github.com/faruktoptas/FancyShowCaseView/blob/master/app/src/main/java/me/toptas/fancyshowcasesample/MainActivity.kt
 */