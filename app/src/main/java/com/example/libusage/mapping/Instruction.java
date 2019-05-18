package com.example.libusage.mapping;

public class Instruction {

   /*
    *   MapActivity used for show map and add marker on current location
    *   Need to give permission in manifest
    *   TODO
    *       <permission android:name="android.permission.ACCESS_FINE_LOCATION" />
            <permission android:name="android.permission.ACCESS_COARSE_LOCATION" />

    *   Need to set <meta-data> tag for google map usage
    *   <!-- google map -->
        TODO <meta-data
                android:name="com.google.android.gms.version"
                android:value="@integer/google_play_services_version" />
        <!-- your google map project api key -->
        TODO <meta-data
                android:name="com.google.android.geo.API_KEY"
                android:value="@string/google_map_key" />

    *   TODO: MapWithPlacesActivity
    *   used for search google places
    *   need to enable google places api in google console
    *
    *   TODO: CustomGooglePlacesActivity
    *   used for create custom google place picker
    *   PlacesAutoCompleteAdapter used for custom place picker
    *   you can customize layout as per your design requirement.
    *   you need to set your map api key in PlaceAutoCompleteAdapter API_KEY
    *   TODO: API_KEY = "YOUR MAP API KEY"
    *
    *   TODO: MyClusterRender
    *   class used to show on map clustering.
    *   You can customize you cluster bg, textSize, textColor etc. in this class.
    *   If we have multiple pins/marker at same place(i.e, near at that place) that time Cluster used to
    *   show single pins/marker and count on that marker.
    *   You can set onClick event on cluster and you can do your stuff.
    *   MapClusterActivity in this activity implement's cluster usage.
    *   for cluster add dependency
    *   TODO
    *    implementation 'com.google.maps.android:android-maps-utils:0.5+'
    *
    *   Need to create MyItem model class
    *   Put setClusterOnMap() method in your MapActivity and addItem in cluster manager.
    *
    *   TODO: Map Styling Usage instruction
    *   You can do map styling.
    *   You just need to map json file to put in row folder and used in in map.
    *   TODO: mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(MapActivity.this, R.raw.map));
    *   Just put this line in onMapReady() method to map styling
    *   You can change road, highway, park etc color on map for that you just need to modify json file
    *   and change color as your need..
    *
    *   TODO:
    *    MUST ENABLE API FOR ROUTE DRAW ON MAP
    *     ~ Google Maps JavaScript API
    *     ~ Google Maps GeoCoding API
    *     ~ Google Maps Places API - For Place Picker
    */
}
