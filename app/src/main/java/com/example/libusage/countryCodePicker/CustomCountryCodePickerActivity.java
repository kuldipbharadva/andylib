package com.example.libusage.countryCodePicker;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.libusage.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class CustomCountryCodePickerActivity extends AppCompatActivity {

    private EditText etSearch;
    private RecyclerView rvCountryCodeList;
    private CountrySelectionAdapter selectionAdapter;
    private AppCompatImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_code_picker);
        initViews();
        settingListeners();
        ArrayList<SelectionListBean> countryCodeList = loadJSONFromAsset();
        if (countryCodeList.size() > 0) {
            setAdapter(countryCodeList);
        }
    }

    private void initViews() {
        etSearch = findViewById(R.id.etSearch);
        rvCountryCodeList = findViewById(R.id.rvCountryCodeList);
    }


    /*Setting up listeners*/
    private void settingListeners() {
        etSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                selectionAdapter.getFilter().filter(cs.toString().trim());
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });
    }


    public ArrayList<SelectionListBean> loadJSONFromAsset() {
        ArrayList<SelectionListBean> countryList = new ArrayList<>();
        String json = null;
        try {
            InputStream is = getAssets().open("CountryCode.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray m_jArry = jsonObject.getJSONArray("countryDetails");
            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                SelectionListBean selectionListBean = new SelectionListBean();
                String country = jo_inside.getString("name");
                String code = jo_inside.getString("dial_code");
                selectionListBean.setName(country);
                selectionListBean.setId(code);
                countryList.add(selectionListBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return countryList;
    }

    private void setAdapter(List<SelectionListBean> dataList) {
        selectionAdapter = new CountrySelectionAdapter(dataList, this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvCountryCodeList.setLayoutManager(mLayoutManager);
        rvCountryCodeList.setAdapter(selectionAdapter);
    }

    /**
     * Sending data back to previous activity
     *
     * @param selectionListBean
     */
    public void sendDataBack(SelectionListBean selectionListBean) {
        Intent intent = new Intent();
        intent.putExtra("Name", "" + selectionListBean.getName());
        intent.putExtra("Id", "" + selectionListBean.getId());
        setResult(1, intent);
        finish();
    }

    private void finishActivity() {
        setResult(2);
        finish();
    }

    @Override
    public void onBackPressed() {
        finishActivity();
    }
}