package com.scarlett.Callback.communicators;

import com.scarlett.pogo.Register.RegisterResponse;

public interface IRegisterActivityCommunicator extends IBaseActivityCommunicator{

    public void onRegisterSuccess(RegisterResponse registerResponse);
    public void onRegisterError();

}
