package com.example.libusage.webApiCall;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.libusage.R;
import com.example.libusage.utilities.CustomDialog;
import com.example.libusage.utilities.MySnackbar;
import com.example.libusage.utilities.NetworkCheck;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("ALL")
public class ApiCalling {

    private static Call call;
    private static Dialog dialog;
    private static String TAG = "apiCalling";

    public static void makeApiCall(final Context context, final String url, final String method, final Map headerMap, final Object requestBody, final boolean isProgressDialogNeeded, final ApiSuccessInterface apiSuccessInterface) {
        Log.d(TAG, "makeApiCall: request :: " + new Gson().toJson(requestBody));
        Log.d(TAG, "makeApiCall: header  :: " + new Gson().toJson(headerMap));
        Log.d(TAG, "makeApiCall: req url :: " + url);
        try {
            if (NetworkCheck.isNetworkAvailable(context)) {
                try {
                    if (isProgressDialogNeeded) {
                        dialog=ProgressDialog.show(context, "", context.getString(R.string.please_wait));
                        dialog.setCancelable(false);
                        dialog.show();
//                        CustomDialog.displayProgress(context);
                    }
                } catch (Exception e) {
                    e.getLocalizedMessage();
                }

                System.setProperty("http.keepAlive", "false");

                if (method.equalsIgnoreCase(RetrofitClient.methodType.GET)) {
                    call = RetrofitClient.getInstanceNew().getServiceCall(url, headerMap);
                } else if (method.equalsIgnoreCase(RetrofitClient.methodType.POST)) {
                    call = RetrofitClient.getInstanceNew().postServiceCall(url, headerMap, requestBody);
                }

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        Log.d(TAG, "onResponse: api response :: " + response.message());
                        try {
                            if (isProgressDialogNeeded) {
//                                CustomDialog.dismissProgress(context);

                                if (dialog != null && dialog.isShowing()) {
                                    dialog.dismiss();
                                }
                            }
                        } catch (Exception e) {
                            e.getLocalizedMessage();
                        }
                        if (response.code() == 403 || response.code() == 404 || response.code() == 500) {
                            MySnackbar.showSnackbar(context, response.message().isEmpty() ? context.getString(R.string.msg_unexpected_error) : response.message(), MySnackbar.SnackbarPosition.TOP, MySnackbar.SnackbarType.FAILED);
                        } else {
                            try {
                                String res = null;
                                if (response.body() != null) {
                                    res = new String(response.body().bytes());
                                }
                                apiSuccessInterface.onSuccess(response.code(), response.message(), res);
                            } catch (IOException e) {
                                e.printStackTrace();
                                MySnackbar.showSnackbar(context, e.getLocalizedMessage().isEmpty() ? context.getString(R.string.msg_unexpected_error) : e.getLocalizedMessage(), MySnackbar.SnackbarPosition.TOP, MySnackbar.SnackbarType.FAILED);
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        Log.d(TAG, "onFailure: api failed :: " + t.getLocalizedMessage());
                        try {
                            if (isProgressDialogNeeded) {
//                                CustomDialog.dismissProgress(context);
                                if (dialog != null && dialog.isShowing()) {
                                    dialog.dismiss();
                                }
                            }

                            apiSuccessInterface.onFailure(t);
                        } catch (Exception e) {
                            e.getLocalizedMessage();
                            MySnackbar.showSnackbar(context, e.getLocalizedMessage().isEmpty() ? context.getString(R.string.msg_unexpected_error) : e.getLocalizedMessage(), MySnackbar.SnackbarPosition.TOP, MySnackbar.SnackbarType.FAILED);
                        }
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
                            ApiCalling.makeApiCall(context, url, method, headerMap, requestBody, true, new ApiSuccessInterface() {
                                @Override
                                public void onSuccess(int resCode, String resMsg, String apiResponse) {
                                    apiSuccessInterface.onSuccess(resCode, resMsg, apiResponse);
                                    Log.d(TAG, "apiCall no network");
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
            MySnackbar.showSnackbar(context, e.getLocalizedMessage().isEmpty() ? context.getString(R.string.msg_unexpected_error) : e.getLocalizedMessage(), MySnackbar.SnackbarPosition.TOP, MySnackbar.SnackbarType.FAILED);
        }
    }

    public interface ApiSuccessInterface {
        void onSuccess(int resCode, String resMsg, String apiResponse);

        void onFailure(Throwable t);
    }
}