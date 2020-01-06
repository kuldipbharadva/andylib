package com.example.libusage.dateFunctions;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFunction {

    /**
     * this function gives you date in specific format.
     */
    public static String getFormattedDate(String inputDate, String inputPattern, String outputPattern) {
        Date parsed;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputPattern, java.util.Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputPattern, java.util.Locale.getDefault());

        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);
        } catch (ParseException e) {
            e.getLocalizedMessage();
        }
        return outputDate;
    }

    /**
     * this function gives you current date as per given pattern(For ex - dd/MM/yyyy, yyyy/MM/dd HH:mm:ss)
     */
    public static String getCurrentDate(String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern, java.util.Locale.getDefault());
        return df.format(Calendar.getInstance().getTime());
    }

    /**
     * this function gives you date from millisecond
     */
    public static String getDateFromMillis(String pattern, long milliSeconds) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, java.util.Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    /**
     * this function gives you millisecond from date
     */
    public static long getMillisFromDate(String date, String pattern) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            Date mDate = sdf.parse(date);
            return mDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * this function gives you current month name - pattern for full name - "MMMM" for short name "MMM"
     */
    public static String getCurrentMonth(String monthPattern) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(monthPattern);
        Date d = new Date();
        return sdf.format(d);
    }

    /**
     * this function get current day name - pattern for full name - "EEEE" for short name "EEE"
     */
    public static String getCurrentDay(String dayPattern) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(dayPattern);
        Date d = new Date();
        return sdf.format(d);
    }

    /**
     * this function gives you current time as per given pattern
     * pattern : HH:mm:ss, hh:mm a, etc...
     */
    public static String getCurrentTime(String timePattern) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(timePattern);
        return sdf.format(new Date());
    }

    /**
     * This function return specific date or month name from entered date.
     * For ex 12/17/2017 10:00:00 AM return 17(date)
     */
    public static String getIntDayFromDate(String date, String yourDatePattern) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(yourDatePattern);
        Date inputDate = null;
        try {
            inputDate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (inputDate != null) {
            return inputDate.getDate() + "";
        } else {
            return "";
        }
    }

    /**
     * This function return specific date from entered date. For ex 12/17/2017 10:00:00 AM return 17(date) and Dec(month)
     * yourDatePattern - ex. MM/dd/yyyy, MM/dd/yyyy HH:mm:ss a
     * monthNamePattern - ex. "MMMM" (Full month name), "MMM" (short month name)
     */
    public static String getMonthNameFromDate(String date, String yourDatePattern, String monthNamePattern) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(yourDatePattern);
        Date inputDate = null;
        try {
            inputDate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat(monthNamePattern);
        Calendar calendar = Calendar.getInstance();
        if (inputDate != null) {
            calendar.set(Calendar.MONTH, inputDate.getMonth());
        }
        simpleDateFormat.setCalendar(calendar);
        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * this function provide you specific date.
     * For ex. : specificDate ~ "17/12/1994"
     * pattern : ex. "dd/MM/yyyy"
     * nextPreviousDateNumber : nextSpecificDate(ex. 2) or previousSpecificDate(ex. -2)
     * return : nextDate output - "19/12/1994", previousDate output - "15/12/1994"
     */
    public static String getNextPreviousDate(String specificDate, String pattern, int nextPreviousDateNumber) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date myDate = null;
        try {
            myDate = sdf.parse(specificDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(myDate);
        calendar.add(Calendar.DAY_OF_YEAR, nextPreviousDateNumber);
        Date newDate = calendar.getTime();
        return sdf.format(newDate);
    }

    /**
     * this function gives you day name from given date
     * dayPattern - full name "EEEE", short name "EEE"
     */
    public static String getDayNameFromDate(String yourDate, String datePattern, String dayPattern) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat inFormat = new SimpleDateFormat(datePattern);
        Date date = null;
        try {
            date = inFormat.parse(yourDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        @SuppressLint("SimpleDateFormat") SimpleDateFormat outFormat = new SimpleDateFormat(dayPattern);
        return outFormat.format(date);
    }

    /** this function gives you hh:mm:ss from millis */
    public static String getHHmmss(long millis) {
        String hmsTime;
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        millis = millis % daysInMilli;

        long elapsedHours = millis / hoursInMilli;
        millis = millis % hoursInMilli;

        long elapsedMinutes = millis / minutesInMilli;
        millis = millis % minutesInMilli;

        long elapsedSeconds = millis / secondsInMilli;

        if (elapsedHours < 10) {
            if (elapsedMinutes < 10) {
                if (elapsedSeconds < 10) {
                    hmsTime = "0" + elapsedHours + ":0" + elapsedMinutes + ":0" + elapsedSeconds;
                } else {
                    hmsTime = "0" + elapsedHours + ":0" + elapsedMinutes + ":" + elapsedSeconds;
                }
            } else {
                if (elapsedSeconds < 10) {
                    hmsTime = "0" + elapsedHours + ":" + elapsedMinutes + ":0" + elapsedSeconds;
                } else {
                    hmsTime = "0" + elapsedHours + ":" + elapsedMinutes + ":" + elapsedSeconds;
                }
            }
        } else {
            if (elapsedMinutes < 10) {
                if (elapsedSeconds < 10) {
                    hmsTime = "" + elapsedHours + ":0" + elapsedMinutes + ":0" + elapsedSeconds;
                } else {
                    hmsTime = "" + elapsedHours + ":0" + elapsedMinutes + ":" + elapsedSeconds;
                }
            } else {
                if (elapsedSeconds < 10) {
                    hmsTime = "" + elapsedHours + ":" + elapsedMinutes + ":0" + elapsedSeconds;
                } else {
                    hmsTime = "" + elapsedHours + ":" + elapsedMinutes + ":" + elapsedSeconds;
                }
            }
        }
        return hmsTime;
    }
}
