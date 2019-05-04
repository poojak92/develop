package com.scarlett.Presenter.activitypresenter;

import android.util.Log;

import com.google.gson.Gson;
import com.scarlett.Callback.communicators.IRetrofitCommunicator;
import com.scarlett.Retrofit.ApiClient;
import com.scarlett.Retrofit.ApiInterface;
import com.scarlett.Utils.DialogUtils;
import com.scarlett.activity.base.BaseBackstackManagerActivity;
import com.scarlett.constants.ApiRequestTag;
import com.scarlett.pogo.Login.LoginResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivityPresenter {

    IRetrofitCommunicator iRetrofitCommunicator;
    BaseBackstackManagerActivity mParentactivity;


    public LoginActivityPresenter(IRetrofitCommunicator iRetrofitCommunicator) {
        this.iRetrofitCommunicator = iRetrofitCommunicator;
        this.mParentactivity = iRetrofitCommunicator.getParentActivity();

    }

    public void doLoginUser(HashMap<String,String> map) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> call = apiService.loginUser(map);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                System.out.println(ApiRequestTag.REQUEST_TAG_REGISTER_USER + "--- "+response.body());
                if(response.isSuccessful())
                {
                    Log.e("REQUEST_TAG_Reg_USER", new Gson().toJson(response));

                    if (response.body().getSuccess()) {
                        iRetrofitCommunicator.onSuccess();

                    }else {
                        DialogUtils.hideProgress();
                        iRetrofitCommunicator.onError(response.body().getError());
                    }

                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                DialogUtils.hideProgress();
                System.out.println(ApiRequestTag.REQUEST_TAG_REGISTER_USER+"  "+t.getMessage());
                iRetrofitCommunicator.onError(t.getMessage());

            }
        });
    }
}
