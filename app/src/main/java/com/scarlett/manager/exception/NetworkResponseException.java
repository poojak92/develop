package com.scarlett.manager.exception;


// For logging network errors on firebase
// e.g. if onErrorResponse getscalled for some reason or other than the expected response status code receives.
public class NetworkResponseException extends Exception {

    public NetworkResponseException(String exceptionMessage){
        super(exceptionMessage);
    }

    public NetworkResponseException(String exceptionMessage,Throwable cause){
        super(exceptionMessage,cause);
    }
}
