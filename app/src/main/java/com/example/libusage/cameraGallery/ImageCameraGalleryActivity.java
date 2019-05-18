package com.example.libusage.cameraGallery;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.libusage.R;
import com.theartofdev.edmodo.cropper.CropImage;

public class ImageCameraGalleryActivity extends AppCompatActivity {

    private Context context;
    private Uri profilePicUri;
    private ImageView ivImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_set);
        initBasic();
    }

    private void initBasic() {
        context = ImageCameraGalleryActivity.this;
        ivImage = findViewById(R.id.ivImage);
        ivImage.setOnClickListener(v -> ImageSelectorFunctions.getImageUploadInstance().openSelectionDialog(context));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageSelectorFunctions.getImageUploadInstance().REQ_GALLERY_CODE) {
            ImageSelectorFunctions.getImageUploadInstance().onResultOfPicSelection(context, ImageSelectorFunctions.getImageUploadInstance().REQ_GALLERY_CODE, resultCode, data);
        } else if (requestCode == ImageSelectorFunctions.getImageUploadInstance().REQ_CAMERA_CODE) {
            ImageSelectorFunctions.getImageUploadInstance().onResultOfPicSelection(context, ImageSelectorFunctions.getImageUploadInstance().REQ_CAMERA_CODE, resultCode, data);
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            profilePicUri = ImageSelectorFunctions.getImageUploadInstance().onResultOfPicSelection(context, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE, resultCode, data);
            if (profilePicUri != null) ivImage.setImageURI(profilePicUri);
        }
    }
}
