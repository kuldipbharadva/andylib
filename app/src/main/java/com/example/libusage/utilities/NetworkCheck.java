package com.example.libusage.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkCheck {

//    public static boolean isNetworkAvailable(Context context) {
//        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetwork = connectivity.getActiveNetworkInfo();
//        return activeNetwork != null;
//    }

    public static boolean isNetworkAvailable(Context context) {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) {


            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED) {


            return false;
        }
        return false;
    }
}
