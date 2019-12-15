package com.example.libusage.webApi;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.andylib.dialog.CustomDialog;
import com.andylib.snackbar.TSnackbar;
import com.example.libusage.R;

import java.io.IOException;
import java.util.Map;

import io.reactivex.annotations.NonNull;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("ALL")
public class ApiCalling {

    private static Call call;
    private static Dialog dialog;
    private static CustomDialog customDialog;

    public static void makeApiCall(Context context, String url, String method, Map headerMap, Object requestBody, boolean isProgressDialogNeeded, ApiSuccessInterface apiSuccessInterface) {
        try {
            if (isNetworkAvailable(context)) {
                try {
                    customDialog = new CustomDialog();
                    if (isProgressDialogNeeded) {
                        customDialog.displayProgress(context);
                    }
                } catch (Exception e) {
                    e.getLocalizedMessage();
                }

                if (method.equalsIgnoreCase(RetrofitClient.methodType.GET)) {
                    call = RetrofitClient.getInstanceNew().getServiceCall(url, headerMap);
                } else if (method.equalsIgnoreCase(RetrofitClient.methodType.POST)) {
                    call = RetrofitClient.getInstanceNew().postServiceCall(url, headerMap, requestBody);
                }

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        Log.d("apiRes", "onResponse: " + response.message());
                        try {
                            if (isProgressDialogNeeded)
                                customDialog.dismissProgress(context);
                        } catch (Exception e) {
                            e.getLocalizedMessage();
                        }
                        if (response.code() == 403 || response.code() == 404 || response.code() == 500) {
                            showSnackBar(context, response.message().isEmpty() ? context.getString(R.string.msg_unexpected_error) : response.message());
                        } else {
                            try {
                                String res = null;
                                if (response.body() != null) {
                                    res = new String(response.body().bytes());
                                }
                                apiSuccessInterface.onSuccess(response.code(), response.message(), res);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        Log.d("apiRes", "onFailure: " + t.getLocalizedMessage());
                        try {
                            if (isProgressDialogNeeded) customDialog.dismissProgress(context);
                            apiSuccessInterface.onFailure(t);
                        } catch (Exception e) {
                            e.getLocalizedMessage();
                        }
                    }
                });
            } else {
                if (dialog == null) {
                    dialog = new Dialog(context, R.style.dialog_full_screen);
                    CustomDialog.noInternetDialog(context, dialog, R.layout.dialog_no_internet, R.id.ok, false, CustomDialog.CustomDialogInterface.match_parent, new CustomDialog.NoInterNetDialogInterface() {
                        @Override
                        public void onOkClicked(Dialog dialog, View view) {
                            if (isNetworkAvailable(context))
                                if (dialog.isShowing()) dialog.dismiss();
                            ApiCalling.makeApiCall(context, url, method, headerMap, requestBody, true, new ApiSuccessInterface() {
                                @Override
                                public void onSuccess(int resCode, String resMsg, String apiResponse) {
                                    apiSuccessInterface.onSuccess(resCode, resMsg, apiResponse);
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
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public interface ApiSuccessInterface {
        void onSuccess(int resCode, String resMsg, String apiResponse);

        void onFailure(Throwable t);
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityMgr != null;
        NetworkInfo networkInfo = connectivityMgr.getActiveNetworkInfo();
        /// if no network is available networkInfo will be null
        return networkInfo != null && networkInfo.isConnected();
    }

    public static void showSnackBar(Context context, String msg) {
        TSnackbar snackbar = TSnackbar.make(((Activity) context).findViewById(android.R.id.content), msg, TSnackbar.LENGTH_SHORT);
        snackbar.setActionTextColor(Color.WHITE);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.RED);
        TextView textView = snackbarView.findViewById(R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

}

//recvfrom failed: ECONNRESET (Connection reset by peer)