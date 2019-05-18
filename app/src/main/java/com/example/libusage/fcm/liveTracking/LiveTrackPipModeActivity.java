package com.example.libusage.fcm.liveTracking;

import android.app.PictureInPictureParams;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.util.Rational;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.libusage.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

@RequiresApi(api = Build.VERSION_CODES.O)
public class LiveTrackPipModeActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = LiveTrackPipModeActivity.class.getSimpleName();
    private HashMap<String, Marker> mMarkers = new HashMap<>();
    private GoogleMap mMap;
    private Button btnPipMode;
    private final PictureInPictureParams.Builder pictureInPictureParamsBuilder =
            new PictureInPictureParams.Builder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pip_live_track);
        initBasic();
        initEventListener();
    }

    private void initBasic() {
        btnPipMode = findViewById(R.id.btnPipMode);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    /* this function handle click event of control */
    private void initEventListener() {
        btnPipMode.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                pictureInPictureMode();
            }
        });

        findViewById(R.id.btnStartLiveTracking).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(LiveTrackPipModeActivity.this, TrackerService.class));
            }
        });

        findViewById(R.id.btnStopLiveTracking).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(LiveTrackPipModeActivity.this, TrackerService.class));
            }
        });
    }


    /* this function open pip mode */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void pictureInPictureMode() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        Rational aspectRatio = new Rational(width, height);
        pictureInPictureParamsBuilder.setAspectRatio(aspectRatio).build();
        enterPictureInPictureMode(pictureInPictureParamsBuilder.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onUserLeaveHint() {
        if (!isInPictureInPictureMode()) {
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;
            Rational aspectRatio = new Rational(width, height);
            pictureInPictureParamsBuilder.setAspectRatio(aspectRatio).build();
            enterPictureInPictureMode(pictureInPictureParamsBuilder.build());
        }
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
        if (isInPictureInPictureMode) {
            btnPipMode.setVisibility(View.GONE);
        } else {
            btnPipMode.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMaxZoomPreference(16);
        /* this is for give map styling */
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map));

//        subscribeToUpdates();
    }

    /* this for firebase database to get data and change pin on live location */
    private void subscribeToUpdates() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("locations");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String previousChildName) {
                setMarker(dataSnapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String previousChildName) {
                setMarker(dataSnapshot);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String previousChildName) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    /* this function add marker on current location which is comes from firebase database */
    private void setMarker(DataSnapshot dataSnapshot) {
        // When a location update is received, put or update
        // its value in mMarkers, which contains all the markers
        // for locations received, so that we can build the
        // boundaries required to show them all on the map at once
        String key = dataSnapshot.getKey();
        HashMap<String, Object> value = (HashMap<String, Object>) dataSnapshot.getValue();
        double lat = Double.parseDouble(value.get("latitude").toString());
        double lng = Double.parseDouble(value.get("longitude").toString());
        Toast.makeText(this, "" + lat + " " + lng, Toast.LENGTH_SHORT).show();
        LatLng location = new LatLng(lat, lng);
        if (!mMarkers.containsKey(key)) {
            mMarkers.put(key, mMap.addMarker(new MarkerOptions().title(key).position(location)));
        } else {
            mMarkers.get(key).setPosition(location);
        }
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : mMarkers.values()) {
            builder.include(marker.getPosition());
        }
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 300));
    }
}