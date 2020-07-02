package com.example.libusage.webApiCall;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

public class RetrofitClient {

    public static String BASE_URL="https://wpstage.a2hosted.com/Topup_Services/";
    private static Retrofit retrofit;

    private static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            OkHttpClient okHttpClient=new OkHttpClient().newBuilder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(false)
                    .build();

            Gson gson=new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }

    public static ApiServiceInterface getInstanceNew() {
        return getRetrofitInstance().create(ApiServiceInterface.class);
    }

    public interface methodType {
        String GET="GET";
        String POST="POST";
        String PUT="PUT";
        String DELETE="DELETE";
    }

    public interface ApiServiceInterface {
        @GET
        Call<RequestBody> getServiceCall(@Url String url, Map headerMap);

        @POST
        Call<RequestBody> postServiceCall(@Url String url, Map headerMap, Object requestBody);


        @Multipart
        @POST("user/ImageUpload")
        Call<RequestBody> imageUpload(@Header("Appsecret") String authorization,
                                         @Part MultipartBody.Part imageFile,
                                         @Part("param") RequestBody param);
    }
}
