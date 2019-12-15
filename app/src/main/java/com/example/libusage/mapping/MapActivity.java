package com.example.libusage.mapping;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.libusage.R;
import com.example.libusage.routeDraw.GoogleMapsPath;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnMapLongClickListener,
        View.OnClickListener {

    /* Our Map */
    private GoogleMap mMap;

    /* To store longitude and latitude from map */
    private double longitude;
    private double latitude;

    /* Buttons */
    private ImageButton buttonSave;
    private ImageButton buttonCurrent;
    private ImageButton buttonView;

    /* Google ApiClient */
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        init();
    }

    private void init() {
        /* Obtain the SupportMapFragment and get notified when the map is ready to be used. */
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        /* Initializing google api client */
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        /* Initializing views and adding onclick listeners */
        buttonSave = findViewById(R.id.buttonSave);
        buttonCurrent = findViewById(R.id.buttonCurrent);
        buttonView = findViewById(R.id.buttonView);
        buttonSave.setOnClickListener(this);
        buttonCurrent.setOnClickListener(this);
        buttonView.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    /* Getting current location */
    private void getCurrentLocation() {
        mMap.clear();
        /* Creating a location object */
        @SuppressLint("MissingPermission") Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location != null) {
            /* Getting longitude and latitude */
            longitude = location.getLongitude();
            latitude = location.getLatitude();

            /* moving the map to location */
            moveMap();
        }
    }

    /* Function to move the map */
    private void moveMap() {
        /* String to display current latitude and longitude */
        String msg = latitude + ", " + longitude;

        /* Creating a LatLng Object to store Coordinates */
        LatLng latLng = new LatLng(latitude, longitude);

        /* Adding marker on map to current location */
        mMap.addMarker(new MarkerOptions()
                .position(latLng) //setting position
                .draggable(true) //Making the marker draggable
                .title("Current Location")); //Adding a title

        //Moving the camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        /* Animating the camera */
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        /* Displaying current coordinates in toast */
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        drawPollyLine();
    }

    @Override
    public void onClick(View v) {
        if (v == buttonCurrent) {
            getCurrentLocation();
            moveMap();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d("onMap", "onConnected: ");
        getCurrentLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("onMap", "onConnected: ");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("onMap", "onConnected: ");
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        /* Clearing all the markers */
        mMap.clear();
        /* Adding a new marker to the current pressed position */
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .draggable(true));
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        /* Getting the coordinates */
        latitude = marker.getPosition().latitude;
        longitude = marker.getPosition().longitude;

        /* Moving the map */
        moveMap();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        /* this line change map styling and json file is used for it. */
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(MapActivity.this, R.raw.map));

        LatLng latLng = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(latLng).draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.setOnMarkerDragListener(this);
        mMap.setOnMapLongClickListener(this);

        mMap.setOnInfoWindowCloseListener(new GoogleMap.OnInfoWindowCloseListener() {
            @Override
            public void onInfoWindowClose(Marker marker) {
                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_pin));
            }
        });
    }

    /* this function draw polyline using routeDraw api */
    private void drawPollyLine() {
        /* used for route draw */
        new GoogleMapsPath(MapActivity.this,
                mMap,
                new LatLng(22.2904654, 70.7850509),
                new LatLng(22.3504888, 70.7848999));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }
}
