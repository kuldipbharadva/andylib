package com.example.libusage.payment.payTm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import java.util.HashMap;
import java.util.Map;

public class PayTmIntegration {

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    private static String TAG = "payTm";

    public PayTmIntegration(Context mContext) {
        PayTmIntegration.mContext = mContext;
    }

    public static void onStartTransaction(String checksumHash, Paytm paytm) {
        PaytmPGService Service = PaytmPGService.getProductionService();
        Map<String, String> paramMap = new HashMap<>();

        /* these are mandatory parameters */
        paramMap.put("MID", "TECHOP10964184510936");
        paramMap.put("ORDER_ID", "1523342168787");
        paramMap.put("CUST_ID", "test111");
        paramMap.put("CHANNEL_ID", "WAP");
        paramMap.put("TXN_AMOUNT", "1");
        paramMap.put("WEBSITE", "");
        paramMap.put("CALLBACK_URL", "https://securegw.paytm.in/theia/paytmCallback");
//        paramMap.put("CHECKSUMHASH", "yDZks+XoWcQ4YZJ+Iod+f/b/7mi5tcGqqQELPhSLQYjGdUfcUnsegcjlsdW797gnsvy4YrHNV8HSIJmdFN0NgWbNTle8wgKFAnSH14crB3A=");
        paramMap.put("INDUSTRY_TYPE_ID", "Retail");


        PaytmOrder Order = new PaytmOrder(paramMap);


        /*
        Paytm Merchant Merchant = new PaytmMerchant(
        "https://pguat.paytm.com/paytmchecksum/paytmCheckSumGenerator.jsp",
        "https://pguat.paytm.com/paytmchecksum/paytmCheckSumVerify.jsp");
        */

        Service.initialize(Order, null);

        Service.startPaymentTransaction(mContext, true, true,
                new PaytmPaymentTransactionCallback() {
                    @Override
                    public void someUIErrorOccurred(String inErrorMessage) {
                        // Some UI Error Occurred in Payment Gateway Activity.
                        // This may be due to initialization of views in
                        // Payment Gateway Activity or may be due to
                        // initialization of webView.
                        // Error Message details the error occurred.
                        Log.d(TAG, "someUIErrorOccurred: error msg : " + inErrorMessage);
                    }

                    @Override
                    public void onTransactionResponse(Bundle inResponse) {
                        Log.d(TAG, "Payment Transaction is successful : " + inResponse);
                        Toast.makeText(mContext, "Payment Transaction response " + inResponse.toString(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void networkNotAvailable() { // If network is not
                        // available, then this
                        // method gets called.
                        Log.d(TAG, "networkNotAvailable: ");
                    }

                    @Override
                    public void clientAuthenticationFailed(String inErrorMessage) {
                        // This method gets called if client authentication
                        // failed. // Failure may be due to following reasons //
                        // 1. Server error or downtime. // 2. Server unable to
                        // generate checksum or checksum response is not in
                        // proper format. // 3. Server failed to authenticate
                        // that client. That is value of paytm_STATUS is 2. //
                        // Error Message describes the reason for failure.
                        Log.d(TAG, "clientAuthenticationFailed: " + inErrorMessage);
                    }

                    @Override
                    public void onErrorLoadingWebPage(int iniErrorCode,
                                                      String inErrorMessage, String inFailingUrl) {
                        Log.d(TAG, "onErrorLoadingWebPage: " + inFailingUrl);
                    }

                    // had to be added: NOTE
                    @Override
                    public void onBackPressedCancelTransaction() {
                        Log.d(TAG, "onBackPressedCancelTransaction: ");
                        Toast.makeText(mContext, "Back pressed. Transaction cancelled", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                        Log.d("LOG", "Payment Transaction Failed " + inErrorMessage);
                        Toast.makeText(mContext, "Payment Transaction Failed ", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
