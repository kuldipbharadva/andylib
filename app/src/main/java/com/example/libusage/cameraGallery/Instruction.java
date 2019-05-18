package com.example.libusage.cameraGallery;

public class Instruction {

   /*
    *  This is mainly used for pick image from gallery or capture from camera.

    *  First you need to give permission in manifest
    *  TODO <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    *  TODO <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />

    *  Need to add following dependency for cropping image
    *  TODO implementation 'com.theartofdev.edmodo:android-image-cropper:2.6.0'

    *  add to manifest : register CropImageActivity in manifest
    *  TODO <activity
                android:name="com.theartofdev.edmodo.cropper.CropImageActivity"
                android:theme="@style/Base.Theme.AppCompat" />
    *
    * Then you need to add ImageSelectorFunctions in your app.
    * And call this class on click of select button for pick or capture image like
    * TODO ImageSelectorFunctions.getImageUploadInstance().openSelectionDialog(context)
    *
    * you can use profilePicUri(Uri) as static to get/set image uri anywhere
    * Done :)
    *
    */
}
