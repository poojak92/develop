package com.scarlett.Retrofit;

import com.scarlett.pogo.Login.LoginResponse;
import com.scarlett.pogo.Register.RegisterResponse;
import com.scarlett.pogo.Upload.UploadResponse;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by lenovo on 23-03-2018.
 */

public interface ApiInterface {
    @FormUrlEncoded
    @POST("api.php")
    Call<RegisterResponse> registerUser(@FieldMap Map<String, String> fieldsMap);

    @FormUrlEncoded
    @POST("api.php")
    Call<LoginResponse> loginUser(@FieldMap Map<String, String> fieldsMap);

    @POST("api.php")
    Call<UploadResponse> upload_file(@Body RequestBody file);
}
