package com.example.libusage.localization;

public class Instruction {

    /*
     * Localization
     * setLocale() used to change language of app and you can store that in preference to maintain selected language.
     * you need to reopen activity by using getIntent() especially when arabic language set.
     * you need to used reopenActivity method when onRestart method called because when you came back to previous
     * activity from next activity that time need to set selected language of that next activity's.
     * why onRestart : because onRestart called when activity goes on pause mode and for manage of selected lang when
     * get back to previous activity.
     * LocalizationTesting activity only for testing of language change effect.
     * NOTE : if there is no activity in back stack then no need to call reopenActivity method.
     *
     */
}
