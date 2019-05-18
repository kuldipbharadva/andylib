package com.example.libusage.socialLogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.andylib.socialLogin.GoogleLogin;
import com.andylib.socialLogin.SocialItem;
import com.andylib.socialLogin.SocialResultCallback;
import com.andylib.util.CommonConfig;
import com.example.libusage.R;

public class GmailLoginActivity extends AppCompatActivity {

    private GoogleLogin gmailLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmail);
        init();
    }

    private void init() {
        gmailLogin = new GoogleLogin(GmailLoginActivity.this, socialLoginInterface);
        findViewById(R.id.btnLogin).setOnClickListener(v -> gmailLogin.signIn());
    }

    private SocialResultCallback socialLoginInterface = new SocialResultCallback() {
        @Override
        public void onSuccess(SocialItem socialModel) {

        }

        @Override
        public void onError(String errorMessage) {

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
//            case AppConfig.SocialLoginCode.GOOGLE_SIGN_IN:
            case CommonConfig.GOOGLE_SIGN_IN :
                gmailLogin.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

}