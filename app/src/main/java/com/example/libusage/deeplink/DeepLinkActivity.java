package com.example.libusage.deeplink;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.libusage.R;

public class DeepLinkActivity extends AppCompatActivity {

    private String linkId = null, eventId = null, walletData;
    private int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deeplink);
        initBasic();
    }

    private void initBasic() {
        findViewById(R.id.btnShareLink).setOnClickListener(v -> shareLink());
    }

    private void shareLink() {
        try {
            Intent share = new Intent(android.content.Intent.ACTION_SEND);
            share.setType("text/html");
            share.putExtra(Intent.EXTRA_SUBJECT, "Deeplink");
            share.putExtra(Intent.EXTRA_TEXT, "https://google.com/");
            startActivity(Intent.createChooser(share, "Share"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* you can get deeplink data with the help oh this method */
    private void getDeepLinkData() {
        Intent appLinkIntent = getIntent();
        try {
            Uri appLinkData = appLinkIntent.getData();
            if (appLinkData != null) {
                if (appLinkIntent.getData().getQuery().contains("eid"))
                    eventId = appLinkIntent.getData().getQueryParameter("eid");
                else if (appLinkIntent.getData().getQuery().contains("linkid"))
                    linkId = appLinkIntent.getData().getQueryParameter("linkid");
            } else if (appLinkIntent.getExtras() != null && appLinkIntent.getExtras().containsKey("eid") && appLinkIntent.getExtras().get("eid").toString().trim().length() > 0) {
                eventId = appLinkIntent.getExtras().get("eid").toString();
            } else if (appLinkIntent.getExtras() != null && appLinkIntent.getExtras().containsKey("WALLET_DATA") && appLinkIntent.getExtras().get("WALLET_DATA") != null) {
//                walletData = appLinkIntent.getExtras().get("WALLET_DATA");
            } else if (appLinkIntent.getExtras() != null && appLinkIntent.getExtras().containsKey("TYPE") && appLinkIntent.getExtras().get("TYPE") != null) {
                type = appLinkIntent.getExtras().getInt("TYPE");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
