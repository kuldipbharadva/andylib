package com.example.libusage.mapping.mapCluster;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.libusage.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;


public class MapClusterActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnMapLongClickListener,
        View.OnClickListener {

    //Our Map
    private GoogleMap mMap;

    //To store longitude and latitude from map
    private double longitude;
    private double latitude;

    //Buttons
    private ImageButton buttonSave;
    private ImageButton buttonCurrent;
    private ImageButton buttonView;

    //Google ApiClient
    private GoogleApiClient googleApiClient;

    // for cluster usage
    private ClusterManager<MyItem> mClusterManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        init();
    }

    private void init() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        //Initializing google api client
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        //Initializing views and adding onclick listeners
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

    //Getting current location
    private void getCurrentLocation() {
        mMap.clear();
        //Creating a location object
        @SuppressLint("MissingPermission") Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location != null) {
            //Getting longitude and latitude
            longitude = location.getLongitude();
            latitude = location.getLatitude();

            //moving the map to location
            moveMap();
        }
    }

    //Function to move the map
    private void moveMap() {
        //String to display current latitude and longitude
        String msg = latitude + ", " + longitude;

        //Creating a LatLng Object to store Coordinates
        LatLng latLng = new LatLng(latitude, longitude);

        //Adding marker to map
        mMap.addMarker(new MarkerOptions()
                .position(latLng) //setting position
                .draggable(true) //Making the marker draggable
                .title("Current Location")); //Adding a title

        //Moving the camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        //Animating the camera
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        //Displaying current coordinates in toast
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
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
//        getCurrentLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        //Clearing all the markers
        mMap.clear();
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
        //Getting the coordinates
        latitude = marker.getPosition().latitude;
        longitude = marker.getPosition().longitude;

        //Moving the map
        moveMap();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.clear();
        setClusterOnMap();
    }

    /* this function set cluster on map and handle click on cluster */
    private void setClusterOnMap() {
        mClusterManager = new ClusterManager<>(this, mMap);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(22.2904463, 70.7848857), 10));
        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);
        mMap.setOnInfoWindowClickListener(mClusterManager);
        mClusterManager.setRenderer(new MyClusterRender(this, mMap, mClusterManager));
        mClusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<MyItem>() {
            @Override
            public boolean onClusterClick(Cluster<MyItem> cluster) {
                Toast.makeText(MapClusterActivity.this, "Cluster Clicked", Toast.LENGTH_SHORT).show();
                //todo
                /* this code is for reference
                Iterator<MyItem> namesIterator = cluster.getItems().iterator();

                List<MyItem> listInCluster = new ArrayList<MyItem>();

                while (namesIterator.hasNext()) {
                    listInCluster.add(namesIterator.next());
                }

                ArrayList<LatLng> arrayList = new ArrayList<>();
                for (int j = 0; j < listInCluster.size(); j++) {
                    arrayList.add(listInCluster.get(j).getPosition());
                }*/
                return true;
            }
        });
        addItems(22.2904463, 70.7848857);
        addItems(22.3024463, 70.7948857);
        addItems(22.3214463, 70.8148857);
        addItems(22.3244463, 70.8248857);
        addItems(22.3644463, 70.8448857);
        addItems(22.3844463, 70.8848857);
        addItems(22.4044463, 70.9048857);
    }

    /* add the latLong list using for loop to the cluster manager */
    private void addItems(Double lat, Double lng) {
        MyItem offsetItem = new MyItem(lat, lng);
        mClusterManager.addItem(offsetItem);
    }
}
