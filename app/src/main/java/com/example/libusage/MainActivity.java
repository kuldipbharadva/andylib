package com.example.libusage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.libusage.bottomSheet.BottomSheetActivity;
import com.example.libusage.cameraGallery.ImageCameraGalleryActivity;
import com.example.libusage.countryCodePicker.CustomCountryCodePickerActivity;
import com.example.libusage.db.LocalDbActivity;
import com.example.libusage.deeplink.DeepLinkActivity;
import com.example.libusage.internetChecking.InternetCheckingActivity;
import com.example.libusage.localization.LocalisationActivity;
import com.example.libusage.locationDialog.LocationDialogUsageActivity;
import com.example.libusage.mapping.MapActivity;
import com.example.libusage.mapping.MapWithPlacesActivity;
import com.example.libusage.mapping.customGooglePlaces.CustomGooglePlacesActivity;
import com.example.libusage.mapping.mapCluster.MapClusterActivity;
import com.example.libusage.mapping.nearByPlaces.NearByPlacesActivity;
import com.example.libusage.parallaxScrolling.ParallaxScrollingActivity;
import com.example.libusage.payment.payTm.PayTmActivity;
import com.example.libusage.payment.paypal.PaypalActivity;
import com.example.libusage.pipMode.PiPModeActivity;
import com.example.libusage.qrCode.QrCodeGeneratorActivity;
import com.example.libusage.qrCode.QrCodeScannerActivity;
import com.example.libusage.recyclerView.simpleSwipeToDelete.SimpleSwipeToDeleteActivity;
import com.example.libusage.recyclerView.swipeToDeleteOption.SwipeToDeleteOptionActivity;
import com.example.libusage.showCaseView.ShowCaseViewUsageActivity;
import com.example.libusage.socialLogin.FacebookLoginActivity;
import com.example.libusage.socialLogin.GmailLoginActivity;
import com.example.libusage.socialLogin.TwitterLoginActivity;
import com.example.libusage.viewpagerUsage.ViewPagerUsageActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickEvent(View v) {
        switch (v.getId()) {
            case R.id.tvCameraGallery:
                intentCall(ImageCameraGalleryActivity.class);
                break;
            case R.id.tvLocalDatabase:
                intentCall(LocalDbActivity.class);
                break;
            case R.id.tvFirebaseDatabase:
                Toast.makeText(MainActivity.this, "Need to setup your app with Firebase and then You can see how it's work.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tvGmailLogin:
                intentCall(GmailLoginActivity.class);
                break;
            case R.id.tvFbLogin:
                intentCall(FacebookLoginActivity.class);
                break;
            case R.id.tvTwitterLogin:
                intentCall(TwitterLoginActivity.class);
                break;
            case R.id.tvDeeplink:
                intentCall(DeepLinkActivity.class);
                break;
            case R.id.tvLocalization:
                intentCall(LocalisationActivity.class);
                break;
            case R.id.tvLocationDialog:
                intentCall(LocationDialogUsageActivity.class);
                break;
            case R.id.tvSimpleMap:
                intentCall(MapActivity.class);
                break;
            case R.id.tvSimpleMapPlaces:
                intentCall(MapWithPlacesActivity.class);
                break;
            case R.id.tvCustomPlaces:
                intentCall(CustomGooglePlacesActivity.class);
                break;
            case R.id.tvNearByPlaces:
                intentCall(NearByPlacesActivity.class);
                break;
            case R.id.tvMapCluster:
                intentCall(MapClusterActivity.class);
                break;
            case R.id.tvPaypal:
                intentCall(PaypalActivity.class);
                break;
            case R.id.tvPayTm:
                intentCall(PayTmActivity.class);
                break;
            case R.id.tvQrScanner:
                intentCall(QrCodeScannerActivity.class);
                break;
            case R.id.tvQrGenerator:
                intentCall(QrCodeGeneratorActivity.class);
                break;
            case R.id.tvCountryCodePicker:
                intentCall(CustomCountryCodePickerActivity.class);
                break;
            case R.id.tvViewPagerUse:
                intentCall(ViewPagerUsageActivity.class);
                break;
            case R.id.tvInternetChecking:
                intentCall(InternetCheckingActivity.class);
                break;
            case R.id.tvPiPMode:
                intentCall(PiPModeActivity.class);
                break;
            case R.id.tvSimpleSwipeToDelete:
                intentCall(SimpleSwipeToDeleteActivity.class);
                break;
            case R.id.tvSwipeToDeleteOption:
                intentCall(SwipeToDeleteOptionActivity.class);
                break;
            case R.id.tvParallaxScrolling:
                intentCall(ParallaxScrollingActivity.class);
                break;
            case R.id.tvShowCase:
                intentCall(ShowCaseViewUsageActivity.class);
                break;
            case R.id.tvBottomSheet:
                intentCall(BottomSheetActivity.class);
                break;
        }
    }

    private void intentCall(Class intentClass) {
        startActivity(new Intent(MainActivity.this, intentClass));
    }
}
