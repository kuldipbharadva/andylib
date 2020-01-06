package com.example.libusage.utilities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import androidx.annotation.NonNull;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Field;
import java.util.Objects;

public class Utility {

    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivity.getActiveNetworkInfo();
        return activeNetwork != null;
    }

    public void hideSoftKeyboard(@NonNull Activity activity) {
        try {
            InputMethodManager inputManager = (InputMethodManager)
                    activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(Objects.requireNonNull(activity.getCurrentFocus()).getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("SdCardPath")
    public static String getPreferencePrefix(Context context) {
        return "/data/data/" + context.getPackageName() + "/shared_prefs/";
    }

    @SuppressLint("HardwareIds")
    public String getDeviceId(Context context) {
        return Settings.Secure.getString(context
                .getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    // Read BuildConfig File Of Main Project From Library To Use Inside The app.
    public static boolean getDebugFlag(Context context) {
        Object o = getBuildConfigValue("DEBUG_FLAG", context);
        if (o instanceof Boolean) {
            return (Boolean) o;
        } else {
            return false;
        }
    }

    public static String getBuildType(Context context) {
        Object o = getBuildConfigValue("BUILD_TYPE", context);
        if (o instanceof String) {
            return (String) o;
        } else {
            return "";
        }
    }

    public static int getDataBaseVersion(Context context) {
        Object o = getBuildConfigValue("DATABASE_VERSION", context);
        if (o instanceof Integer) {
            return (Integer) o;
        } else {
            return 1;
        }
    }

    /**
     * Gets a field from the project's BuildConfig. This is useful when, for example, flavors * are used at the project level to set custom fields. * @param context Used to find the correct file * @param fieldName The name of the field-to-access * @return The value of the field, or {@code null} if the field is not found.
     */
    private static Object getBuildConfigValue(String fieldName,Context context) {
        try {
            Class<?> clazz = Class.forName(context.getPackageName() + ".BuildConfig");
            Field field = clazz.getField(fieldName);
            return field.get(null);
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}