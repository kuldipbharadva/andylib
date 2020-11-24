package com.example.libusage.utilities;

import android.content.Context;
import android.widget.EditText;

import java.util.regex.Pattern;

public class Validations {

    public static boolean isShowSnackBar = true;

    public static boolean isBlank(Context context, EditText editText, String emptyMsg, int snackBarPosition) {
        String _strEditTextVal = editText.getText().toString().trim();
        if (_strEditTextVal.length() == 0) {
            showSnackBar(context, emptyMsg, snackBarPosition);
            editText.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    public static boolean isBlank(Context context, EditText editText, String emptyMsg, String errorMsg, int snackBarPosition) {
        String _strEditTextVal = editText.getText().toString().trim();
        if (_strEditTextVal.length() == 0) {
            showSnackBar(context, emptyMsg, snackBarPosition);
            editText.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    public static boolean isBlank(Context context, EditText editText, String emptyMsg, String errorMsg, int snackBarPosition, int minVal, int maxVal) {
        String _strEditTextVal = editText.getText().toString().trim();
        boolean b = _strEditTextVal.length() < minVal || _strEditTextVal.length() > maxVal;
        if (_strEditTextVal.length() == 0) {
            showSnackBar(context, emptyMsg, snackBarPosition);
            editText.requestFocus();
            return false;
        }
        if (b) {
            showSnackBar(context, errorMsg, snackBarPosition);
            editText.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    public static boolean isValidNumberDecimal(Context context, EditText editText, String msg, int snackBarPosition) {
        if (editText.getText().toString().trim().equals(".") || editText.getText().toString().trim().equals(",")) {
            MySnackbar.showSnackbar(context, msg, MySnackbar.SnackbarPosition.TOP, MySnackbar.SnackbarType.FAILED);
            return false;
        }
        return true;
    }

    public static boolean isValueGreaterThanZero(Context context, EditText editText, String msg, int snackBarPosition) {
        if (Double.parseDouble(editText.getText().toString().trim()) <= 0) {
            MySnackbar.showSnackbar(context, msg, MySnackbar.SnackbarPosition.TOP, MySnackbar.SnackbarType.FAILED);
            return false;
        }
        return true;
    }

    public static boolean isValidateSpecialChar(Context context, EditText editText, String msg, int snackBarPosition) {
        String text = editText.getText().toString().trim();
        //Pattern regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!]");
        Pattern regex = Pattern.compile("[$&+:;=\\\\?@#|/'<>^*()%!]");
        if (regex.matcher(text).find()) {
            showSnackBar(context, msg, snackBarPosition);
            return false;
        }
        return true;
    }

    public static boolean isValidEmail(Context context, EditText editText, String msg, int snackBarPosition) {
        if (!(editText.getText().toString().trim().toLowerCase()
                .matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))) {
            showSnackBar(context, msg, snackBarPosition);
            return false;
        } else {
            return true;
        }
    }

    public static boolean isPasswordMacth(Context context, EditText edtPassword1, EditText edtPassword2, String msg, int snackbarPosition) {
        String strPassword1 = edtPassword1.getText().toString().trim();
        String strPassword2 = edtPassword2.getText().toString().trim();
        if (!strPassword1.equals(strPassword2)) {
            showSnackBar(context, msg, snackbarPosition);
            edtPassword2.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    private static void showSnackBar(Context context, String msg, int snackBarPosition) {
        if (isShowSnackBar) {
            if (snackBarPosition == MySnackbar.SnackbarPosition.TOP) {
                MySnackbar.showSnackbar(context, msg, MySnackbar.SnackbarPosition.TOP, MySnackbar.SnackbarType.FAILED);
            } else {
                MySnackbar.showSnackbar(context, msg, MySnackbar.SnackbarPosition.BOTTOM, MySnackbar.SnackbarType.FAILED);
            }
        }
    }
}
