package com.example.libusage.utilities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.CalendarContract;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.libusage.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressWarnings("ALL")
public class Functions {

    public static void nextAnim(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    public static void backAnim(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    public static void addEventInCalendar(Context context, String datePattern, String date) {
        String day = null, monthNumber = null, year = null;
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat format1 = new SimpleDateFormat(datePattern);
            Date dt1 = format1.parse(date);
            @SuppressLint("SimpleDateFormat") DateFormat dayFormat = new SimpleDateFormat("dd");
            @SuppressLint("SimpleDateFormat") DateFormat monthFormat = new SimpleDateFormat("MM");
            @SuppressLint("SimpleDateFormat") DateFormat yearFormat = new SimpleDateFormat("yyyy");
            day = dayFormat.format(dt1);
            monthNumber = monthFormat.format(dt1);
            year = yearFormat.format(dt1);
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(Integer.parseInt(year), Integer.parseInt(monthNumber) - 1, Integer.parseInt(day));
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, context.getString(R.string.app_name))
                .putExtra(CalendarContract.Events.EVENT_LOCATION, "Location");
        context.startActivity(intent);
    }

    /* this function open device map app and show location of given latLong */
    public static void openMap(Context context, double lat, double lng) {
        String geoUri = "http://maps.google.com/maps?q=loc:" + lat + "," + lng;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
        context.startActivity(intent);
    }

    /* this function share play store app link */
    public static void shareLink(Context context) {
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name));
            Uri uri = Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName());
            String sAux = uri.toString();
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            context.startActivity(Intent.createChooser(i, "Choose"));
        } catch (Exception e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }
    }

    public static void showKeyboard(Activity context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        View v = context.getCurrentFocus();
        if (v != null)
            imm.showSoftInput(v, 0);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View currentFocusedView = activity.getCurrentFocus();
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), 0);
        }
    }
}
