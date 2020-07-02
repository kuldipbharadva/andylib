package com.example.libusage.utilities;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.Calendar;

public class CardValidation {

    // for card date check
    public static final String SLASH_SEPARATOR = "/";
    public static final int MAX_LENGTH_CARD_NUMBER_WITH_SPACES = 19;
    public static final String SPACE_SEPERATOR = " ";

    /* this code validate visa card */
    public static boolean validateVisaCard(String cardNo, Context context) {
        String ptVisa = "^4[0-9]{6,}$";
        String no = null;
        if (cardNo.length() == 19) {
            String[] arr = cardNo.split(" ");
            no = arr[0] + "" + arr[1] + "" + arr[2] + "" + arr[3];
            if (no.length() == 16 && no.matches(ptVisa)) {
                return true;
            } else {
                MySnackbar.showSnackbar(context, "Invalid Visa card", MySnackbar.SnackbarPosition.TOP, MySnackbar.SnackbarType.FAILED);
            }
        } else {
            MySnackbar.showSnackbar(context, "Invalid Visa card", MySnackbar.SnackbarPosition.TOP, MySnackbar.SnackbarType.FAILED);
        }

        return false;
    }

    /* this code validate master card */
    public static boolean validateMasterCard(String cardNo, Context context) {
        String ptMasterCard = "^5[1-5][0-9]{5,}$";
        String no = null;
        if (cardNo.length() == 19) {
            String[] arr = cardNo.split(" ");
            no = arr[0] + "" + arr[1] + "" + arr[2] + "" + arr[3];
            if (no.length() == 16 && no.matches(ptMasterCard)) {
                return true;
            } else {
                MySnackbar.showSnackbar(context, "Invalid Mastercard", MySnackbar.SnackbarPosition.TOP, MySnackbar.SnackbarType.FAILED);
            }
        } else {
            MySnackbar.showSnackbar(context, "Invalid Mastercard", MySnackbar.SnackbarPosition.TOP, MySnackbar.SnackbarType.FAILED);
        }

        return false;
    }

    /* this code validate card CVV */
    public static boolean validateCVV(String cvvNo, Context context) {
        if (cvvNo.length() == 3) {
            return true;
        } else if (cvvNo.length() == 2 || cvvNo.length() == 1) {
            MySnackbar.showSnackbar(context, "Invalid CVV", MySnackbar.SnackbarPosition.TOP, MySnackbar.SnackbarType.FAILED);
        } else {
            MySnackbar.showSnackbar(context, "Enter CVV", MySnackbar.SnackbarPosition.TOP, MySnackbar.SnackbarType.FAILED);
        }
        return false;
    }

    /* this code validate card expiry date*/
    public static String handleExpiration(String month, String year) {
        return handleExpiration(month + year);
    }

    private static String handleExpiration(@NonNull String dateYear) {
        String expiryString = dateYear.replace(SLASH_SEPARATOR, "");
        String text;
        if (expiryString.length() >= 2) {
            String mm = expiryString.substring(0, 2);
            String yy;
            text = mm;
            try {
                if (Integer.parseInt(mm) > 12) {
                    mm = "12"; // Cannot be more than 12.
                }
            } catch (Exception e) {
                mm = "01";
            }
            if (expiryString.length() >= 4) {
                yy = expiryString.substring(2, 4);
                try {
                    Integer.parseInt(yy);
                } catch (Exception e) {
                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    yy = String.valueOf(year).substring(2);
                }
                text = mm + SLASH_SEPARATOR + yy;
            } else if (expiryString.length() > 2) {
                yy = expiryString.substring(2);
                text = mm + SLASH_SEPARATOR + yy;
            }
        } else {
            text = expiryString;
        }
        return text;
    }

    /* this code adding space seprator in card number */
    public static String handleCardNumber(String inputCardNumber) {

        return handleCardNumber(inputCardNumber, SPACE_SEPERATOR);
    }

    public static String handleCardNumber(String inputCardNumber, String seperator) {

        String formattingText = inputCardNumber.replace(seperator, "");
        String text;

        if (formattingText.length() >= 4) {

            text = formattingText.substring(0, 4);

            if (formattingText.length() >= 8) {
                text += seperator + formattingText.substring(4, 8);
            } else if (formattingText.length() > 4) {
                text += seperator + formattingText.substring(4);
            }

            if (formattingText.length() >= 12) {
                text += seperator + formattingText.substring(8, 12);
            } else if (formattingText.length() > 8) {
                text += seperator + formattingText.substring(8);
            }

            if (formattingText.length() >= 16) {
                text += seperator + formattingText.substring(12);
            } else if (formattingText.length() > 12) {
                text += seperator + formattingText.substring(12);
            }

            return text;

        } else {
            text = formattingText.trim();
        }

        return text;
    }
}
