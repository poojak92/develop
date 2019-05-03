package com.scarlett.manager;

import com.android.volley.Request;


import java.util.Map;

public class ApiManager extends BaseApiManager {


    public ApiManager(String mRequestTag, IMiddlewareResponse mIMiddlewareResponse) {
        super(mRequestTag, mIMiddlewareResponse);
    }


    //TODO make calls here after
    // TODO for example - fetcing user profile

    /*
    public void getUserProfile(){
        makeCall("API_URL", Request.Method.GET,true);
    }

    */



    public void doRegisterUser(final String name, final String email ,final String mobileno , final String password , final String action) {
        makeRegisterCall(name,email,mobileno,password,action);
    }



}
