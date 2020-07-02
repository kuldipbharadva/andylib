package com.example.libusage;

import android.app.Application;

import com.example.libusage.dbRealm.RealmHelper;
import com.example.libusage.internetChecking.ConnectivityReceiver;
import com.facebook.FacebookSdk;
import com.facebook.ads.AudienceNetworkAds;

public class MyApplication extends Application {

    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        RealmHelper.getRealmInstance(getApplicationContext());

        // Initialize the Audience Network SDK for facebook ads
        AudienceNetworkAds.initialize(this);

        mInstance = this;
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    /* for internet checking service */
    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}
