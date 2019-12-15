package com.example.libusage.imagePickerLikeWhatsapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libusage.R;
import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.fxn.utility.ImageQuality;
import com.fxn.utility.PermUtil;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WhatsAppImgPickerActivity extends AppCompatActivity {

    private Context mContext;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private MyAdapter myAdapter;
    private Options options;
    private ArrayList<String> returnValue=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whatsapp_img_picker);
        ButterKnife.bind(this);
        initBasicTask();
    }

    private void initBasicTask() {
        mContext=WhatsAppImgPickerActivity.this;

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter=new MyAdapter(this);

        options=Options.init()
                .setRequestCode(100)
                .setCount(3)
                .setFrontfacing(false)
                .setImageQuality(ImageQuality.LOW)
                .setPreSelectedUrls(returnValue)
                .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)
                .setPath("/new") /* this path is your image capture storage path */
        ;
        recyclerView.setAdapter(myAdapter);

        findViewById(R.id.fab).setOnClickListener((View view) -> {
            options.setPreSelectedUrls(returnValue);
            Pix.start(WhatsAppImgPickerActivity.this, options);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.e("val", "requestCode ->  " + requestCode+"  resultCode "+resultCode);
        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {
                returnValue=data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
                myAdapter.addImage(returnValue);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        if (requestCode == PermUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Pix.start(WhatsAppImgPickerActivity.this, options);
            } else {
                Toast.makeText(mContext, "Approve permissions to open ImagePicker", Toast.LENGTH_LONG).show();
            }
        }
    }
}
