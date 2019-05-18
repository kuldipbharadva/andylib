package com.example.libusage.mapping.customGooglePlaces;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.libusage.R;

import java.io.IOException;
import java.util.List;

public class CustomGooglePlacesActivity extends AppCompatActivity {

    private EditText edt_enter_search;
    private ListView suggestionListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_google_places);
        initBasic();
    }

    private void initBasic() {
        edt_enter_search = findViewById(R.id.edt_enter_search);
        suggestionListView = findViewById(R.id.suggestionListView);

        settingAdapter();

        suggestionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /* When you search location and select from list this method called and you need to do task here after selection of location. */
                String location = (String) adapterView.getItemAtPosition(i);
                Intent in = new Intent();
                in.putExtra("Location", "" + location);
                setResult(10, in);

                Toast.makeText(CustomGooglePlacesActivity.this, "" + location, Toast.LENGTH_SHORT).show();
                Address selectedLocation = getLocationFromAddress(location);
                if (selectedLocation != null) {
                    Toast.makeText(CustomGooglePlacesActivity.this, "Lat : " + selectedLocation.getLatitude() + "\nLong : " + selectedLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /* Google places autocomplete adapter list view */
    private void settingAdapter() {
        final PlaceAutoCompleteAdapter adapter = new PlaceAutoCompleteAdapter(this, R.layout.row_choose_location_list_item);
        suggestionListView.setAdapter(adapter);
        edt_enter_search.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString());
            }
        });
    }

    /* Getting address of the selected location from GeoCoder */
    private Address getLocationFromAddress(String strAddress) {
        Geocoder coder = new Geocoder(this);
        List<Address> address = null;
        try {
            address = coder.getFromLocationName(strAddress, 5);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (address == null) {
            return null;
        }
        Address location = null;
        if (!address.isEmpty())
            location = address.get(0);

        return location;
    }
}