package com.scarlett.Presenter.activitypresenter;

import com.android.volley.VolleyError;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scarlett.Callback.communicators.IRegisterActivityCommunicator;
import com.scarlett.Utils.CommanUtils;
import com.scarlett.Utils.DialogUtils;
import com.scarlett.activity.base.BaseBackstackManagerActivity;
import com.scarlett.constants.ApiRequestTag;
import com.scarlett.manager.ApiManager;
import com.scarlett.manager.BaseApiManager;
import com.scarlett.pogo.Register.RegisterResponse;

import java.io.IOException;

public class RegisterActivityPresenter {

    IRegisterActivityCommunicator iRegisterActivityCommunicator;
    BaseBackstackManagerActivity mParentactivity;
    CommanUtils mCommanUtils;

    public RegisterActivityPresenter(IRegisterActivityCommunicator iRegisterActivityCommunicator) {
        this.iRegisterActivityCommunicator = iRegisterActivityCommunicator;
        this.mParentactivity = iRegisterActivityCommunicator.getParentActivity();
        this.mCommanUtils = new CommanUtils(mParentactivity);
    }

    public void doRegisterUser(final String name, final String email ,final String mobileno , final String password ) {
        ApiManager apiManager = new ApiManager(ApiRequestTag.REQUEST_TAG_REGISTER_USER, new BaseApiManager.IMiddlewareResponse() {
            @Override
            public void onResponse(Object response) {
                RegisterResponse registerResponse = null;
                try {
                    registerResponse = new ObjectMapper().readValue(response.toString(), RegisterResponse.class);
                    if(registerResponse != null){
                        if(registerResponse.getSuccess()) {
                            iRegisterActivityCommunicator.onRegisterSuccess(registerResponse);
                        }else {
                            DialogUtils.showNetworkErrorDialog(mParentactivity);
                            iRegisterActivityCommunicator.onRegisterError();
                        }
                    }else {
                        DialogUtils.showNetworkErrorDialog(mParentactivity);
                        iRegisterActivityCommunicator.onRegisterError();
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
                iRegisterActivityCommunicator.onRegisterError();
            }
        });

        if(CommanUtils.isNetworkAvailable()){
            DialogUtils.showProgress(mParentactivity,"");
            apiManager.doRegisterUser(name,email,mobileno,password,"rg");
        }else {
            DialogUtils.showNoInternetConnectionDialog(mParentactivity);
        }
    }
}
