package com.example.libusage.mapping;

import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.codemybrainsout.placesearch.PlaceSearchDialog;
import com.example.libusage.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResponse;
import com.google.android.gms.location.places.PlacePhotoResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;


public class MapWithPlacesActivity extends FragmentActivity implements OnMapReadyCallback {

    //Our Map
    private GoogleMap mMap;

    //Google ApiClient
    private GoogleApiClient googleApiClient;

    private GeoDataClient mGeoDataClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_places);
        init();
    }

    private void init() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Initializing google api client
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .build();

        // code is for places
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getName().toString()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 12.0f));
                Log.d("mPhoto", "onPlaceSelected: place id : " + place.getId());
                getPhotos(place.getId());
            }

            @Override
            public void onError(Status status) {

            }
        });
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng latLng = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(latLng).draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    /* this function show dialog of search location call this function when you want to search location - library used */
    private void googlePlacesDialog() {
        PlaceSearchDialog placeSearchDialog = new PlaceSearchDialog.Builder(this)
                .setHintText("Enter location name")
                .setHintTextColor(R.color.mt_gray6)
                .setNegativeText("CANCEL")
                .setNegativeTextColor(R.color.colorPrimary)
                .setPositiveText("OK")
                .setPositiveTextColor(R.color.colorAccent)
                .setLocationNameListener(locationName -> {
                    //set textView or editText
                    Toast.makeText(MapWithPlacesActivity.this, "" + locationName, Toast.LENGTH_SHORT).show();
                })
                .build();
        placeSearchDialog.show();
    }


    // Request photos and metadata for the specified place.
    private void getPhotos(String placeId) {
        //        final String placeId = "ChIJa147K9HX3IAR-lwiGIQv9i4";
        //        final String placeId = "ChIJrTLr-GyuEmsRBfy61i59si0";
        mGeoDataClient = Places.getGeoDataClient(this, null);
        final Task<PlacePhotoMetadataResponse> photoMetadataResponse = mGeoDataClient.getPlacePhotos(placeId);
        photoMetadataResponse.addOnCompleteListener(task -> {
            // Get the list of photos.
            PlacePhotoMetadataResponse photos = task.getResult();
            // Get the PlacePhotoMetadataBuffer (metadata for all of the photos).
            PlacePhotoMetadataBuffer photoMetadataBuffer = photos.getPhotoMetadata();
            // Get the first photo in the list.
            if (photoMetadataBuffer != null) {
                PlacePhotoMetadata photoMetadata = photoMetadataBuffer.get(0);

                // Get the attribution text.
                CharSequence attribution = photoMetadata.getAttributions();
                // Get a full-size bitmap for the photo.
                Task<PlacePhotoResponse> photoResponse = mGeoDataClient.getPhoto(photoMetadata);
                photoResponse.addOnCompleteListener(task1 -> {
                    PlacePhotoResponse photo = task1.getResult();
                    Bitmap bitmap = photo.getBitmap();
                    ImageView ivImages = findViewById(R.id.ivImage);
                    if (bitmap != null) {
                        ivImages.setImageBitmap(bitmap);
                        Log.d("mPhoto", "onComplete: bitmap : " + bitmap);
                    }
                });
            }
        });
    }
}

// Nearby places api use -
// dependency ~ implementation 'com.google.android.gms:play-services-places:15.0.0'
// add PlaceAutocompleteFragment code - above given in init().
// you can find photo from place id, for that getPhoto() available and photoId get from place.