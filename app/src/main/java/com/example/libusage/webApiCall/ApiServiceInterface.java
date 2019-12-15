package com.example.libusage.webApiCall;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiServiceInterface {

    @FormUrlEncoded
    @POST("yourApiPostURL")
    Call<ResponseBody> yourApiPostURL(@Field("AccessKey") String accessKey);
}