package com.example.libusage.ads;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.libusage.R;
import com.facebook.ads.Ad;
import com.facebook.ads.AdChoicesView;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.RewardedVideoAd;
import com.facebook.ads.RewardedVideoAdListener;

import java.util.ArrayList;
import java.util.List;

public class FacebookAdsActivity extends AppCompatActivity {

    private Context mContext;

    private String TAG = "tag";

    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads_facebook);
        mContext = FacebookAdsActivity.this;

        String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        AdSettings.addTestDevice(android_id);

        bannerAds();
        interstitialAds();
        initNativeFb();
        rewardVideoAds();
    }

    /* facebook banner ads */
    private void bannerAds() {

        // Instantiate an AdView object.
        // NOTE: The placement ID from the Facebook Monetization Manager identifies your App.
        // To get test ads, add IMG_16_9_APP_INSTALL# to your placement id. Remove this when your app is ready to serve real ads.

        AdView adView = new AdView(mContext, "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID", AdSize.BANNER_HEIGHT_50);
        // Find the Ad Container
        LinearLayout adContainer = findViewById(R.id.bannerContainer);
        // Add the ad view to your activity layout
        adContainer.addView(adView);
        // Request an ad
        adView.loadAd();
    }

    /* facebook interstitial ads */
    private void interstitialAds() {
        interstitialAd = new InterstitialAd(this, "YOUR_PLACEMENT_ID");
        // Set listeners for the Interstitial Ad
        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
                Log.e(TAG, "Interstitial ad displayed.");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                Log.e(TAG, "Interstitial ad dismissed.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                // Show the ad
                interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                Log.d(TAG, "Interstitial ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                Log.d(TAG, "Interstitial ad impression logged!");
            }
        });

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown
        interstitialAd.loadAd();

        findViewById(R.id.btnLoadInterstitialAds).setOnClickListener(v -> {
            if (interstitialAd.isAdLoaded()) {
                interstitialAd.show();
            } else {
                interstitialAd.loadAd();
                Toast.makeText(mContext, "Ads not load", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private NativeAd fbNative;
    private void initNativeFb() {
        LinearLayout llNative = (LinearLayout) findViewById(R.id.llNative);
        fbNative = new NativeAd(this, "YOUR_PLACEMENT_ID");

        fbNative.setAdListener(new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {

            }

            @Override
            public void onError(Ad ad, AdError adError) {

            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Add the Ad view into the ad container.
                LayoutInflater inflater = LayoutInflater.from(mContext);
                LinearLayout adViewFb = (LinearLayout) inflater.inflate(R.layout.activity_ads_facebook_native_layout, llNative, false);
                llNative.removeAllViews();
                llNative.addView(adViewFb);

                // Create native UI using the ad metadata.
                ImageView nativeAdIcon = adViewFb.findViewById(R.id.native_ad_icon);
                TextView nativeAdTitle = adViewFb.findViewById(R.id.native_ad_title);
                MediaView nativeAdMedia = adViewFb.findViewById(R.id.native_ad_media);
                TextView nativeAdSocialContext = adViewFb.findViewById(R.id.native_ad_social_context);
                TextView nativeAdBody = adViewFb.findViewById(R.id.native_ad_body);
                Button nativeAdCallToAction = adViewFb.findViewById(R.id.native_ad_call_to_action);

                // Set the Text.
                nativeAdTitle.setText(fbNative.getAdHeadline());
                nativeAdSocialContext.setText(fbNative.getAdSocialContext());
                nativeAdBody.setText(fbNative.getAdBodyText());
                nativeAdCallToAction.setText(fbNative.getAdCallToAction());

                // Download and display the ad icon.
                NativeAd.Image adIcon = fbNative.getAdIcon();
//                NativeAd.downloadAndDisplayImage(adIcon, nativeAdIcon);

                // Download and display the cover image.
//                nativeAdMedia.setNativeAd(fbNative);

                // Add the AdChoices icon
                LinearLayout adChoicesContainer = (LinearLayout) adViewFb.findViewById(R.id.ad_choices_container);
                AdChoicesView adChoicesView = new AdChoicesView(mContext, fbNative, true);
                adChoicesContainer.addView(adChoicesView);

                // Register the Title and CTA button to listen for clicks.
                List<View> clickableViews = new ArrayList<>();
                clickableViews.add(nativeAdTitle);
                clickableViews.add(nativeAdCallToAction);
                fbNative.registerViewForInteraction(llNative, new MediaView(mContext));
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        });
        // Request an ad
        fbNative.loadAd();
    }

    private void rewardVideoAds() {
        RewardedVideoAd rewardedVideoAd = new RewardedVideoAd(this, "YOUR_PLACEMENT_ID");
        rewardedVideoAd.setAdListener(new RewardedVideoAdListener() {
            @Override
            public void onError(Ad ad, AdError error) {
                // Rewarded video ad failed to load
                Log.e(TAG, "Rewarded video ad failed to load: " + error.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Rewarded video ad is loaded and ready to be displayed
                Log.d(TAG, "Rewarded video ad is loaded and ready to be displayed!");
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Rewarded video ad clicked
                Log.d(TAG, "Rewarded video ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Rewarded Video ad impression - the event will fire when the
                // video starts playing
                Log.d(TAG, "Rewarded video ad impression logged!");
            }

            @Override
            public void onRewardedVideoCompleted() {
                // Rewarded Video View Complete - the video has been played to the end.
                // You can use this event to initialize your reward
                Log.d(TAG, "Rewarded video completed!");

                // Call method to give reward
                // giveReward();
            }

            @Override
            public void onRewardedVideoClosed() {
                // The Rewarded Video ad was closed - this can occur during the video
                // by closing the app, or closing the end card.
                Log.d(TAG, "Rewarded video ad closed!");
            }
        });
        rewardedVideoAd.loadAd();
    }
}
