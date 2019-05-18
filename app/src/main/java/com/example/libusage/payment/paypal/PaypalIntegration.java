package com.example.libusage.payment.paypal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import java.math.BigDecimal;

public class PaypalIntegration {

    public static final int PAYPAL_REQUEST_CODE = 123;
    private static final String PAYPAL_CLIENT_ID = "AYlt71RTS3_yrFiVUWJ2JMNAVfYRKDhHarR_GLncQRg1DsjnKGAgHMUFWG_vPDgVMSklvVv8HC4RvMbX";
    private Context mContext;

    public PaypalIntegration(Context mContext) {
        this.mContext = mContext;
    }

    public void getPayment(String paymentAmount) {
        //Getting the amount from editText
//        paymentAmount = editTextAmount.getText().toString();

        //Creating a paypal payment
        startPaypalService();
        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(paymentAmount)), "USD", "Simplified Coding Fee", PayPalPayment.PAYMENT_INTENT_SALE);

        //Creating Paypal Payment activity intent
        Intent intent = new Intent(mContext, PaymentActivity.class);

        //putting the paypal configuration to the intent
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, getConfig());

        //Putting paypal payment to the intent
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        //Starting the intent activity for result
        //the request code will be used on the method onActivityResult
        ((Activity) mContext).startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    public void startPaypalService() {
        Intent intent = new Intent(mContext, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, getConfig());
        mContext.startService(intent);
    }

    //Paypal Configuration Object
    private PayPalConfiguration getConfig() {
        return new PayPalConfiguration()
                // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
                // or live (ENVIRONMENT_PRODUCTION)
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(PAYPAL_CLIENT_ID);
    }
}