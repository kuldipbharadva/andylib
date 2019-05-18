package com.example.libusage.payment.payTm;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.libusage.R;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PayTmActivity extends AppCompatActivity implements PaytmPaymentTransactionCallback {

    private TextView textViewPrice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_tm);
        initBasic();
    }

    private void initBasic() {
        textViewPrice = findViewById(R.id.textViewPrice);
        findViewById(R.id.buttonBuy).setOnClickListener(view -> {
            //calling the method generateCheckSum() which will generate the paytm checksum for payment
            generateCheckSum();
        });
    }

    /* backend need to setup api for checksum and after we need to call api for checksum */
    private void generateCheckSum() {

        //getting the tax amount first.
        String txnAmount = textViewPrice.getText().toString().trim();

        String BASE_URL = "http://192.168.101.1/paytm/";
        //creating a retrofit object.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //creating the retrofit api service
        ApiService apiService = retrofit.create(ApiService.class);

        //creating paytm object
        //containing all the values required
        final Paytm paytm = new Paytm(
                Constants.M_ID,
                Constants.CHANNEL_ID,
                txnAmount,
                Constants.WEBSITE,
                Constants.CALLBACK_URL,
                Constants.INDUSTRY_TYPE_ID
        );

        //creating a call object from the apiService
        Call<Checksum> call = apiService.getChecksum(
                paytm.getmId(),
                paytm.getOrderId(),
                paytm.getCustId(),
                paytm.getChannelId(),
                paytm.getTxnAmount(),
                paytm.getWebsite(),
                paytm.getCallBackUrl(),
                paytm.getIndustryTypeId()
        );

        //making the call to generate checksum
        call.enqueue(new Callback<Checksum>() {
            @Override
            public void onResponse(@NonNull Call<Checksum> call,@NonNull Response<Checksum> response) {
                //once we get the checksum we will initiailize the payment.
                //the method is taking the checksum we got and the paytm object as the parameter
                if (response.body() != null) {
                    PayTmIntegration.onStartTransaction(response.body().getChecksumHash(), paytm);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Checksum> call, @NonNull Throwable t) {
                Log.d("payTm", "onFailure: " + t.getLocalizedMessage());
                Toast.makeText(PayTmActivity.this, "Failed : " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onTransactionResponse(Bundle inResponse) {

    }

    @Override
    public void networkNotAvailable() {

    }

    @Override
    public void clientAuthenticationFailed(String inErrorMessage) {

    }

    @Override
    public void someUIErrorOccurred(String inErrorMessage) {

    }

    @Override
    public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {

    }

    @Override
    public void onBackPressedCancelTransaction() {

    }

    @Override
    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {

    }
}
