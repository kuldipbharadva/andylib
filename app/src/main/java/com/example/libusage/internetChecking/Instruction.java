package com.example.libusage.internetChecking;

public class Instruction {

    /*
     *   InternetCheckingActivity show the usage of network checking service in background.
     *   We can check network/internet in background. So we need to create broadcast service and register in manifest.
     *   With the help of this service we can check network continuously in activity or manage our task.
     *   TODO:
     *    ~ Manifest file code
     *      <receiver
                android:name=".internetChecking.ConnectivityReceiver"
                android:enabled="true">
                <intent-filter>
                    <action
                        android:name="android.net.conn.CONNECTIVITY_CHANGE"
                        tools:ignore="BatteryLife" />
                </intent-filter>
             </receiver>
     *
     *   TODO:
     *     ~ Application class
     *     public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
                ConnectivityReceiver.connectivityReceiverListener = listener;
           }

     *   Put ConnectivityReceiver file in app
     *   Usage available in InternetCheckingActivity
     */
}
