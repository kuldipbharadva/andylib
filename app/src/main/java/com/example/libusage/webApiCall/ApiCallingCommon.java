package com.example.libusage.webApiCall;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.libusage.R;
import com.example.libusage.utilities.CustomDialog;
import com.example.libusage.utilities.NetworkCheck;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiCallingCommon {

    private static Dialog dialog = null;

    public static void makeCall(final Context context, final Call<ResponseBody> call, final boolean isDialogNeeded, final ApiSuccessInterface apiSuccessInterface) {
        if (NetworkCheck.isNetworkAvailable(context)) {
            if (isDialogNeeded) {
                dialog = ProgressDialog.show(context, "", context.getString(R.string.please_wait));
            }
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                    try {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        String res = null;
                        if (response.body() != null) {
                            res = new String(response.body().bytes());
                        }
                        apiSuccessInterface.onSuccess(response.code(), response.message(), res);
                        Log.d("apiCall", "onResponse: success : " + res);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    apiSuccessInterface.onFailure(t);
                    Log.d("apiCall", "onFailure: failed : " + t.getLocalizedMessage());
                }
            });
        } else {
            if (dialog == null) {
                dialog = new Dialog(context, R.style.dialog_full_screen);
                CustomDialog.noInternetDialog(context, dialog, R.layout.dialog_no_internet, R.id.ok, false, CustomDialog.CustomDialogInterface.match_parent, new CustomDialog.NoInterNetDialogInterface() {
                    @Override
                    public void onOkClicked(Dialog dialog, View view) {
                        if (NetworkCheck.isNetworkAvailable(context))
                            if (dialog.isShowing()) dialog.dismiss();
                        ApiCallingCommon.makeCall(context, call, isDialogNeeded, new ApiSuccessInterface() {
                            @Override
                            public void onSuccess(int code, String msg, String response) {
                                apiSuccessInterface.onSuccess(code, msg, response);
                                Log.d("apiCall", "apiCall no network");
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                apiSuccessInterface.onFailure(t);
                            }
                        });
                    }
                });
            }
        }
    }

    public interface ApiSuccessInterface {
        void onSuccess(int code, String msg, String response);

        void onFailure(Throwable t);
    }
}
