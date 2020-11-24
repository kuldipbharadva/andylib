package com.example.libusage.webApiCall;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.libusage.utilities.Functions;
import com.example.libusage.utilities.GetRealPath;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageApiCall {

    /* this function call image upload api */
    public void apiCallImageUpload(Context context,  Uri imgUri) {
        File file = null;
        //Uri imgUri = null;
        if (imgUri != null) {
            String filePath = GetRealPath.getRealPathFromUri(context, imgUri);
            if (filePath != null && !filePath.isEmpty()) {
                file = new File(filePath);
            }
        }
        if (file != null && file.exists()) {
            RequestBody imageFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), imageFile);
            RequestBody param = RequestBody.create(MediaType.parse("text/plain"), "other param");

            Call<ResponseBody> call = RetrofitClient.getInstanceNew().imageUpload("PYKuBOdOsne9oJyhKmLt6HDt8Mwt62I5CSS", body, param);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
//                    if (response.body() != null && response.body().getStatusCode() == 200) {
//                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                }
            });
        }
    }
}

/*
    For multiple image send with list multipart

    List<MultipartBody.Part> list = new ArrayList<>();
    for (int i = 0; i < imageCustomModels.size(); i++) {
        if (imageCustomModels.get(i).isSelected()) {
            File file = new File(imageCustomModels.get(i).getUrl());
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part imageRequest = MultipartBody.Part.createFormData("files", file.getName(), requestFile);
            list.add(imageRequest);
        }
    }

*/