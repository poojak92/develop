package com.scarlett.Presenter.activitypresenter;

import android.util.Log;

import com.google.gson.Gson;
import com.scarlett.Callback.communicators.IRetrofitCommunicator;
import com.scarlett.Retrofit.ApiClient;
import com.scarlett.Retrofit.ApiInterface;
import com.scarlett.Utils.DialogUtils;
import com.scarlett.activity.base.BaseBackstackManagerActivity;
import com.scarlett.constants.ApiRequestTag;
import com.scarlett.pogo.Register.RegisterResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivityPresenter {

    IRetrofitCommunicator iRegisterActivityCommunicator;
    BaseBackstackManagerActivity mParentactivity;


    public RegisterActivityPresenter(IRetrofitCommunicator iRegisterActivityCommunicator) {
        this.iRegisterActivityCommunicator = iRegisterActivityCommunicator;
        this.mParentactivity = iRegisterActivityCommunicator.getParentActivity();

    }

    public void doRegisterUser(HashMap<String,String> map) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<RegisterResponse> call = apiService.registerUser(map);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                System.out.println(ApiRequestTag.REQUEST_TAG_REGISTER_USER + "--- "+response.body());
                if(response.isSuccessful())
                {
                    Log.e("REQUEST_TAG_Reg_USER", new Gson().toJson(response));

                    if (response.body().getSuccess()) {
                        iRegisterActivityCommunicator.onSuccess();
                    }else {
                        DialogUtils.hideProgress();
                        iRegisterActivityCommunicator.onError(response.body().getError());
                    }

                }
            }
            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                DialogUtils.hideProgress();
                System.out.println(ApiRequestTag.REQUEST_TAG_REGISTER_USER+"  "+t.getMessage());
                iRegisterActivityCommunicator.onError(t.getMessage());

            }
        });

        /*ApiManager apiManager = new ApiManager(ApiRequestTag.REQUEST_TAG_REGISTER_USER, new BaseApiManager.IMiddlewareResponse() {
            @Override
            public void onResponse(Object response) {
                RegisterResponse registerResponse = null;
                try {
                    registerResponse = new ObjectMapper().readValue(response.toString(), RegisterResponse.class);
                    if(registerResponse != null){
                        if(registerResponse.getSuccess()) {
                            iRetrofitCommunicator.onSuccess(registerResponse);
                        }else {
                            DialogUtils.showNetworkErrorDialog(mParentactivity);
                            iRetrofitCommunicator.onError();
                        }
                    }else {
                        DialogUtils.showNetworkErrorDialog(mParentactivity);
                        iRetrofitCommunicator.onError();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                DialogUtils.hideProgress();
            }
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.hideProgress();
                DialogUtils.showNetworkErrorDialog(mParentactivity);
                iRetrofitCommunicator.onError();
            }
        });

        if(CommanUtils.isNetworkAvailable()){
            DialogUtils.showProgress(mParentactivity,"");
            apiManager.doRegisterUser(name,email,mobileno,password,"rg");
        }else {
            DialogUtils.showNoInternetConnectionDialog(mParentactivity);
        }*/
    }
}
