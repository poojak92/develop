package com.scarlett.Model.Upload;

import android.content.Context;


import com.scarlett.Retrofit.ApiClient;
import com.scarlett.Retrofit.ApiInterface;
import com.scarlett.Utils.CommanUtils;
import com.scarlett.pogo.Upload.UploadResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadFile {
   public static void get_UploadFile(MultipartBody requestBody, final Context context, final String Tag, final upload_response upload_response)
     {
         ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
         Call<UploadResponse> call = apiInterface.upload_file(requestBody);
         call.enqueue(new Callback<UploadResponse>() {
             @Override
             public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
                 System.out.println(Tag+response.body());
                 if(response.isSuccessful())
                 {
                     boolean result_success=response.body().getSuccess();
                     upload_response.temp_onResponse(result_success);
                 }
             }
             @Override
             public void onFailure(Call<UploadResponse> call, Throwable t) {
                 new CommanUtils(context).showToast(context,t.getMessage());
                 System.out.println(Tag+t.getMessage());
             }
         });
     }
    public interface upload_response {
        void temp_onResponse(boolean temp_responce);
    }
}
