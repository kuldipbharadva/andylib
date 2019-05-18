package com.example.libusage.socialLogin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;

import com.andylib.socialLogin.FacebookLogin;
import com.andylib.socialLogin.SocialItem;
import com.andylib.socialLogin.SocialResultCallback;
import com.example.libusage.R;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;

import java.security.MessageDigest;

public class FacebookLoginActivity extends AppCompatActivity {

    private FacebookLogin facebookLogin;
    private SocialResultCallback socialInterface = new SocialResultCallback() {
        @Override
        public void onSuccess(SocialItem socialModel) {

        }

        @Override
        public void onError(String errorMessage) {
            Log.d("fbLogin", "onError: " + errorMessage);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmail);
        init();
    }

    private void init() {
        facebookLogin = new FacebookLogin(FacebookLoginActivity.this, socialInterface);
        findViewById(R.id.btnLogin).setOnClickListener(v -> facebookLogin.signIn());
        findViewById(R.id.btnLogout).setOnClickListener(v -> logout());
        getHashKey();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebookLogin.onActivityResult(requestCode, resultCode, data);
    }

    public void logout() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            LoginManager.getInstance().logOut();
            Log.d("TAG", "logout: ");
        }
    }

    public void getHashKey() {
        try {
            @SuppressLint("PackageManagerGetSignatures") PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String sign = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Log.d("TAG", "hash key : " + sign);
            }
        } catch (Exception e) {
            Log.d("TAG", "getHashKey: exception : " + e.getLocalizedMessage());
        }
    }
}
