package com.example.libusage.socialLogin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.libusage.R;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterLoginActivity extends AppCompatActivity {

    static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
    static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
    static final String PREF_KEY_TWITTER_LOGIN = "isTwitterLoggedIn";
    static final String TWITTER_CALLBACK_URL = "https://www.google.com/";
    // Twitter oauth urls
    static final String URL_TWITTER_AUTH = "auth_url";
    static final String URL_TWITTER_OAUTH_VERIFIER = "oauth_verifier";
    static final String URL_TWITTER_OAUTH_TOKEN = "oauth_token";
    static String TWITTER_CONSUMER_KEY = "A680hYmb8OgirWN0JFbO3tfSm";
    static String TWITTER_CONSUMER_SECRET = "AHTNOQymFoRoEIlbSgDMu0ujgzIy8EOHP5e3cIC2Zz0PLJ3YJY";
    // Preference Constants
    static String PREFERENCE_NAME = "twitter_oauth";
    private static SharedPreferences mSharedPreferences;
    private static Twitter twitter;
    private static RequestToken requestToken;
    private String TAG = "twitterLogin";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter_login);
        init();
    }

    private void init() {
        // Check if twitter keys are set
        if (TWITTER_CONSUMER_KEY.trim().length() == 0 || TWITTER_CONSUMER_SECRET.trim().length() == 0) {
            // Internet Connection is not present
            Log.d(TAG, "init: twit : Please set your twitter oauth tokens first!");
            return;
        }
        mSharedPreferences = getApplicationContext().getSharedPreferences("TwitterLogin", 0);

        findViewById(R.id.btnLogin).setOnClickListener(v -> {
            twit();
            loginToTwitter();
        });

        findViewById(R.id.btnLogout).setOnClickListener(v -> logoutFromTwitter());
    }

    private boolean isTwitterLoggedInAlready() {
        // return twitter login status from Shared Preferences
        return mSharedPreferences.getBoolean(PREF_KEY_TWITTER_LOGIN, false);
    }

    private void loginToTwitter() {
        // Check if already logged in
        if (!isTwitterLoggedInAlready()) {
            ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setOAuthAuthenticationURL("https://api.twitter.com/oauth2/token");
            builder.setOAuthAccessTokenURL("https://api.twitter.com/oauth/access_token");
            builder.setOAuthAuthorizationURL("https://api.twitter.com/oauth/authorize");
            builder.setOAuthRequestTokenURL("https://api.twitter.com/oauth/request_token");
            builder.setRestBaseURL("https://api.twitter.com/1.1/");
            builder.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
            builder.setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET);
            Configuration configuration = builder.build();
//            AccessToken accessToken = new AccessToken("1303524031-OZE83MxTeF40058qffYSvSEnaDZFvpb7VNcNGNK", "sVMSKayK0TvG4Q8nZZUlpoFTkX9jjS9aXlpfwaQE1atXk");
//            twitter.setOAuthAccessToken(accessToken);

            TwitterFactory factory = new TwitterFactory(configuration);
            twitter = factory.getInstance();

            try {
                requestToken = twitter.getOAuthRequestToken();
                this.startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse(requestToken.getAuthenticationURL())));
            } catch (TwitterException e) {
                e.printStackTrace();
            }
        } else {
            // user already logged into twitter
            Toast.makeText(getApplicationContext(),
                    "Already Logged into twitter", Toast.LENGTH_LONG).show();
        }
    }

    private void logoutFromTwitter() {
        // Clear the shared preferences
        SharedPreferences.Editor e = mSharedPreferences.edit();
        e.remove(PREF_KEY_OAUTH_TOKEN);
        e.remove(PREF_KEY_OAUTH_SECRET);
        e.remove(PREF_KEY_TWITTER_LOGIN);
        e.apply();
    }

    private void twit() {
        if (!isTwitterLoggedInAlready()) {
            Uri uri = getIntent().getData();
            if (uri != null && uri.toString().startsWith(TWITTER_CALLBACK_URL)) {
                // oAuth verifier
                String verifier = uri
                        .getQueryParameter(URL_TWITTER_OAUTH_VERIFIER);

                try {
                    // Get the access token
                    AccessToken accessToken = twitter.getOAuthAccessToken(
                            requestToken, verifier);

                    // Shared Preferences
                    SharedPreferences.Editor e = mSharedPreferences.edit();

                    // After getting access token, access token secret
                    // store them in application preferences
                    e.putString(PREF_KEY_OAUTH_TOKEN, accessToken.getToken());
                    e.putString(PREF_KEY_OAUTH_SECRET,
                            accessToken.getTokenSecret());
                    // Store login status - true
                    e.putBoolean(PREF_KEY_TWITTER_LOGIN, true);
                    e.apply(); // save changes

                    Log.e("Twitter OAuth Token", "> " + accessToken.getToken());

                    // Getting user details from twitter
                    // For now i am getting his name only
                    long userID = accessToken.getUserId();
                    User user = twitter.showUser(userID);

                } catch (Exception e) {
                    // Check log for login errors
                    Log.e("Twitter Login Error", "> " + e.getMessage());
                }
            }
        }
    }
}