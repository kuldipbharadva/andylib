package com.example.libusage.webApiCall;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.libusage.utilities.Functions;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageApiCall {

    /* this function call image upload api */
    public void apiCallImageUpload(Context context) {
        File file = null;
        Uri imgUri = null;
        if (imgUri != null) {
            String filePath = Functions.getRealPathFromUri(context, imgUri);
            if (filePath != null && !filePath.isEmpty()) {
                file = new File(filePath);
            }
        }
        if (file != null && file.exists()) {
            RequestBody imageFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), imageFile);
            RequestBody param = RequestBody.create(MediaType.parse("text/plain"),  "other param");

            Call<RequestBody> call = RetrofitClient.getInstanceNew().imageUpload("PYKuBOdOsne9oJyhKmLt6HDt8Mwt62I5CSS", body, param);
            call.enqueue(new Callback<RequestBody>() {
                @Override
                public void onResponse(@NonNull Call<RequestBody> call, @NonNull Response<RequestBody> response) {
//                    if (response.body() != null && response.body().getStatusCode() == 200) {
//                    }
                }

                @Override
                public void onFailure(@NonNull Call<RequestBody> call, @NonNull Throwable t) {
                }
            });
        }
    }
}
