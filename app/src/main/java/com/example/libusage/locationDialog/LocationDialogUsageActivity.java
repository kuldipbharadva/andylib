package com.example.libusage.locationDialog;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.andylib.log.PrintLog;
import com.andylib.util.CommonConfig;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.DateFormat;
import java.util.Date;

public class LocationDialogUsageActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        ResultCallback<LocationSettingsResult> {

    private Context context;
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    protected GoogleApiClient mGoogleApiClient;
    protected LocationRequest mLocationRequest;
    protected LocationSettingsRequest mLocationSettingsRequest;
    protected FusedLocationProviderClient mFusedLocationClient;
    protected Location mCurrentLocation;
    protected String mLatitudeLabel;
    protected String mLongitudeLabel;
    protected String mLastUpdateTimeLabel;
    protected String mLastUpdateTime;
    protected Boolean mRequestingLocationUpdates = false;
    private double lat, lng;
    private Dialog dialogForLocationWait;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBasic();
    }

    private void initBasic() {
        context = LocationDialogUsageActivity.this;
        buildGoogleApiClient();
        createLocationRequest();
        buildLocationSettingsRequest();
//        dialogForLocationWait = ProgressDialog.show(context, "", "Fetching location...");
        openCheckLocationSettingsDialog();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();

        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    /* this function open gps location on alert dialog */
    protected void openCheckLocationSettingsDialog() {
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(
                        mGoogleApiClient,
                        mLocationSettingsRequest
                );
        result.setResultCallback(this);
    }


    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Within {@code onPause()}, we pause location updates, but leave the
        // connection to GoogleApiClient intact.  Here, we resume receiving
        // location updates if the user has requested them.
        if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
        if (dialogForLocationWait != null && dialogForLocationWait.isShowing())
            dialogForLocationWait.dismiss();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CommonConfig.PermissionCode.ACCESS_FINE_LOCATION:
            case CommonConfig.PermissionCode.ACCESS_COARSE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //code for grant
                    openCheckLocationSettingsDialog();
                } else {
                    //code for deny
                    PrintLog.Log(context, "TAG", "onRequestPermissionsResult", "Location Permission Denied", Log.INFO);
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.d("locationResult", "onActivityResult: location OK clicked");
                        PrintLog.Log(context, "TAG", "User agreed to make required location settings changes.", "", Log.INFO);
                        startLocationUpdates();
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.d("locationResult", "onActivityResult: location No Thanks clicked");
                        PrintLog.Log(context, "TAG", "User chose not to make required location settings changes.", "", Log.INFO);
                        onBackPressed();
                        break;
                }
                break;
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        PrintLog.Log(context, "TAG", "Connected to GoogleApiClient", "", Log.INFO);

        // If the initial location was never previously requested, we use
        // FusedLocationApi.getLastLocation() to get it. If it was previously requested, we store
        // its value in the Bundle and check for it in onCreate(). We
        // do not request it again unless the user specifically requests location updates by pressing
        // the Start Updates button.
        //
        // Because we cache the value of the initial location in the Bundle, it means that if the
        // user launches the activity,
        // moves to a new location, and then changes the device orientation, the original location
        // is displayed as the activity is re-created.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
//            mCurrentLocation = LocationServices.getFusedLocationProviderClient(context).getLastLocation().getResult();
//            mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        if (mCurrentLocation == null) {
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        PrintLog.Log(context, "TAG", "Connection suspended", "", Log.INFO);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        PrintLog.Log(context, "TAG", "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode(), "", Log.INFO);
    }

    @Override
    public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
        final Status status = locationSettingsResult.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                PrintLog.Log(context, "TAG", "All location settings are satisfied.", "", Log.INFO);
                startLocationUpdates();
                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                PrintLog.Log(context, "TAG", "Location settings are not satisfied. Show the user a dialog to" +
                        " upgrade location settings ", "", Log.INFO);
                try {
                    // status.startResolutionForResult(LocationDialogUsageActivity.this, REQUEST_CHECK_SETTINGS);
                    this.startIntentSenderForResult(status.getResolution().getIntentSender(), REQUEST_CHECK_SETTINGS, null, 0, 0, 0, null);
                } catch (IntentSender.SendIntentException e) {
                    PrintLog.Log(context, "TAG", "PendingIntent unable to execute request.", "", Log.INFO);
                }
                break;
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                PrintLog.Log(context, "TAG", "Location settings are inadequate, and cannot be fixed here. Dialog " + "not created.", "", Log.INFO);
                break;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        if (location != null) {
            if (dialogForLocationWait != null && dialogForLocationWait.isShowing())
                dialogForLocationWait.dismiss();
            stopLocationUpdates();
            lat = location.getLatitude();
            lng = location.getLongitude();
        }
    }

    private void startLocationUpdates() {
        try {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
//            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient,
                    mLocationRequest,
                    this
            ).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(@NonNull Status status) {
                    mRequestingLocationUpdates = true;
                }
            });

//            mFusedLocationClient.getLastLocation()
//                    .addOnSuccessListener(LocationDialogUsageActivity.this, new OnSuccessListener<Location>() {
//                        @Override
//                        public void onSuccess(Location location) {
//                            /* Got last known location. In some rare situations this can be null. */
//                            if (location != null) {
//                                /* Logic to handle location object */
//                                mRequestingLocationUpdates = true;
//                            }
//                        }
//                    });
        } catch (Exception e) {
            e.getMessage();
        }
    }

    /* this function stop location updating */
    protected void stopLocationUpdates() {
        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        mFusedLocationClient.removeLocationUpdates(new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                if (mFusedLocationClient != null) {
                    mRequestingLocationUpdates = false;
                }
            }
        });
    }
}
