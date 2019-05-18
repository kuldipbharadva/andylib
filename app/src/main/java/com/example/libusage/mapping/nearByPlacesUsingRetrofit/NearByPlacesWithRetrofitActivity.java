package com.example.libusage.mapping.nearByPlacesUsingRetrofit;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.andylib.apicall.ApiCallback;
import com.andylib.apicall.CustomApiCall;
import com.andylib.util.CommonConfig;
import com.example.libusage.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.libusage.NearByPlaces.NearByPlacesReq;
import com.libusage.NearByPlaces.NearByPlacesRes;
import com.libusage.NearByPlaces.Result;

import java.util.HashMap;

public class NearByPlacesWithRetrofitActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private Context context;
    private GoogleMap mMap;
    double latitude;
    double longitude;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private Marker mCurrLocationMarker;
    private LocationRequest mLocationRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_by_places);
        initBasic();
    }

    private void initBasic() {
        context = NearByPlacesWithRetrofitActivity.this;

        /* Check if Google Play Services Available or not */
        if (!CheckGooglePlayServices()) {
            Log.d("onCreate", "Finishing test case since Google Play Services are not available");
            finish();
        } else {
            Log.d("onCreate", "Google Play Services available.");
        }

        /* Obtain the SupportMapFragment and get notified when the map is ready to be used. */
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    private boolean CheckGooglePlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        0).show();
            }
            return false;
        }
        return true;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // mMap.setMapType(GoogleMap.MAP_TYPE_NONE);

        //Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

        findViewById(R.id.btnRestaurant).setOnClickListener(v -> {
            mMap.clear();
            apiCallNearByPlaces("restaurant");
        });

        findViewById(R.id.btnHospital).setOnClickListener(v -> {
            mMap.clear();
            apiCallNearByPlaces("hospital");
        });

        Button btnSchool = findViewById(R.id.btnSchool);
        btnSchool.setOnClickListener(v -> {
            mMap.clear();
            apiCallNearByPlaces("school");
        });
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("onLocationChanged", "entered");

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        /* Place current location marker */
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        /* move map camera */
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
        Toast.makeText(context, "Your Current Location", Toast.LENGTH_LONG).show();

        Log.d("onLocationChanged", String.format("latitude:%.3f longitude:%.3f", latitude, longitude));

        /* stop location updates */
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            Log.d("onLocationChanged", "Removing Location Updates");
        }
        Log.d("onLocationChanged", "Exit");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    /* this function call nearBy places api and give json response (20 places in response you need to req next page param to more record) */
    private void apiCallNearByPlaces(String placesType) {
        CommonConfig.WsPrefix = "https://maps.googleapis.com/maps/api/place/nearbysearch/";
        NearByPlacesReq req = new NearByPlacesReq();
        req.setLocation(latitude + "," + longitude);
        req.setSensor("true");
        req.setRadius("500");
        req.setType(placesType);
        req.setKey(getString(R.string.google_map_key));
        new CustomApiCall(context,
                req,
                new String[]{"json", "", CommonConfig.WsMethodType.GET},
                new HashMap<>(),
                CommonConfig.serviceCallFrom.GENERAL_WS_CALL,
                new ApiCallback() {
                    @Override
                    public void success(String responseData) {
                        Log.d("apiCallRes", "success: " + responseData);
                        NearByPlacesRes nearByPlacesRes = new Gson().fromJson(responseData, NearByPlacesRes.class);
                        if (nearByPlacesRes != null) {
                            if (nearByPlacesRes.getError_message() != null) {
                                Toast.makeText(context, "" + nearByPlacesRes.getError_message(), Toast.LENGTH_LONG).show();
                            }
                            if (nearByPlacesRes.getResults().size() > 0) {
                                for (int i = 0; i < nearByPlacesRes.getResults().size(); i++) {
                                    Result result = nearByPlacesRes.getResults().get(i);
                                    MarkerOptions markerOptions = new MarkerOptions();
                                    LatLng latLng = new LatLng(nearByPlacesRes.getResults().get(i).getGeometry().getLocation().getLat(),
                                            nearByPlacesRes.getResults().get(i).getGeometry().getLocation().getLng());
                                    markerOptions.position(latLng);
                                    markerOptions.title(result.getName() + " : " + result.getVicinity());
                                    markerOptions.draggable(true);
                                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
                                    mMap.addMarker(markerOptions);
                                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                    mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                                }
                            }
                        }
                    }

                    @Override
                    public void failure(String responseData) {
                        Toast.makeText(context, "Failed : " + responseData, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
