package com.example.libusage.payment.paypal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.libusage.R;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

public class PaypalActivity extends AppCompatActivity {

    private EditText etAmount;
    private TextView tvId, tvStatus, tvAmount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypal);
        castingControl();
        init();
    }

    private void castingControl() {
        etAmount = findViewById(R.id.etAmount);
        tvId = findViewById(R.id.tvId);
        tvStatus = findViewById(R.id.tvStatus);
        tvAmount = findViewById(R.id.tvAmount);
    }

    private void init() {
        PaypalIntegration paypalIntegration = new PaypalIntegration(PaypalActivity.this);
        findViewById(R.id.btnPay).setOnClickListener(v -> paypalIntegration.getPayment(etAmount.getText().toString()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //If the result is from paypal
        if (requestCode == PaypalIntegration.PAYPAL_REQUEST_CODE) {
            //If the result is OK i.e. user has not canceled the payment
            if (resultCode == Activity.RESULT_OK) {
                //Getting the payment confirmation
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                //if confirmation is not null
                if (confirm != null) {
                    try {
                        //Getting the payment details
                        String paymentDetails = confirm.toJSONObject().toString();
                        JSONObject jsonDetails = new JSONObject(paymentDetails);
                        showPaymentDetails(jsonDetails);
                        Log.d("paymentExample", paymentDetails);

                    } catch (JSONException e) {
                        Log.d("paymentExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.d("paymentExample", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.d("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }

    private void showPaymentDetails(JSONObject jsonDetails) {
        try {
            tvId.setText(jsonDetails.getString("id"));
            tvStatus.setText(jsonDetails.getString("state"));
            tvAmount.setText(etAmount.getText());
        } catch (Exception e) {
            Log.d("paymentExample", "showPaymentDetails: exception : " + e.getLocalizedMessage());
        }
    }

    @Override
    public void onDestroy() {
        stopService(new Intent(PaypalActivity.this, PayPalService.class));
        super.onDestroy();
    }
}
