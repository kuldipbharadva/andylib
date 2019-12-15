package com.example.libusage.imagePickFromGalleryMultiple;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.libusage.R;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class ImagePickerActivity extends AppCompatActivity {

    @BindView(R.id.frameUpload)
    FrameLayout frameUpload;
    @BindView(R.id.rvSelectedImages)
    RecyclerView rvSelectedImages;

    private Context mContext;
    private ArrayList<String> selectedImagesList;
    private final int REQ_CODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_picker);
        ButterKnife.bind(this);
        initBasicTask();
    }

    private void initBasicTask() {
        mContext=ImagePickerActivity.this;

        frameUpload.setOnClickListener(v -> ImagePickerActivityPermissionsDispatcher.onAllowStorageWithPermissionCheck(ImagePickerActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE) {
            if (data != null) {
                selectedImagesList=new ArrayList<>();
                ArrayList<Uri> imagesUriList=new ArrayList<>();

                /* this if work when multiple image select from gallery and you will get uri from path
                 * if you need base64 from uri then function os available
                 * when select single pic then else condition work */

                if (data.getClipData() != null) {
                    int count=data.getClipData().getItemCount(); //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
                    for (int i=0; i < count; i++) {
                        Uri imageUri=data.getClipData().getItemAt(i).getUri();
                        imagesUriList.add(imageUri);
                        Log.d("onResultData", "onActivityResult: imgUri :: " + imageUri);
                        getBase64FromUri(imageUri);
                    }
                    rvSelectedImages.setAdapter(new SelectedImagesAdapter(mContext, imagesUriList));
                    rvSelectedImages.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
                    rvSelectedImages.setHasFixedSize(true);
                } else {
                    try {
                        String imagePath=Objects.requireNonNull(data.getData()).getPath();
                        Uri uri=data.getData();
                        Log.d("onResultData", "onActivityResult: imgPath :: " + imagePath);
                        Log.d("onResultData", "onActivityResult: imgUri :: " + uri);

                        /* base64 string */
                        byte[] b=getBytes(uri);
                        String imageString=Base64.encodeToString(b, Base64.DEFAULT);
                        Log.d("onResultData", "onActivityResult: base64 :: " + imageString);

                        /* get byte[] from base64 string */
                        byte[] decodeBase64=Base64.decode(imageString, Base64.DEFAULT);
                        Bitmap decodedImage=BitmapFactory.decodeByteArray(decodeBase64, 0, decodeBase64.length);

                        selectedImagesList.add(imageString);
                        imagesUriList.add(uri);

                        rvSelectedImages.setAdapter(new SelectedImagesAdapter(mContext, imagesUriList));
                        rvSelectedImages.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
                        rvSelectedImages.setHasFixedSize(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /* this function give base64 string from image uri */
    public void getBase64FromUri(Uri uri) {
        try {
            byte[] b=getBytes(uri);
            String imageString=Base64.encodeToString(b, Base64.DEFAULT);
            selectedImagesList.add(imageString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* this function give byte[] from uri */
    public byte[] getBytes(Uri uri) {
        InputStream inputStream;
        try {
            inputStream=getContentResolver().openInputStream(uri);
            ByteArrayOutputStream byteBuffer=new ByteArrayOutputStream();
            int bufferSize=1024;
            byte[] buffer=new byte[bufferSize];

            int len=0;
            while ((len=inputStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }
            return byteBuffer.toByteArray();
        } catch (Exception e) {
            e.getLocalizedMessage();
            return null;
        }
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onAllowStorage() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQ_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ImagePickerActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onDeniedStorage() {
    }

    @OnNeverAskAgain({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onNeverAskStorage() {
    }
}
