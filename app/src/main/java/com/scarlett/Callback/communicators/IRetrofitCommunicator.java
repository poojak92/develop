package com.scarlett.Callback.communicators;

import com.scarlett.pogo.Register.RegisterResponse;

public interface IRetrofitCommunicator extends IBaseActivityCommunicator{

    public void onSuccess();
    public void onError(String error);

}
