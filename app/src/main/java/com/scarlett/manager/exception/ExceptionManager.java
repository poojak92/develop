package com.scarlett.manager.exception;


import android.util.Log;

public class ExceptionManager {

    // Logs non-fatal exception to the firebase crashlytics
    public static void logException(Throwable exception){
        Log.d("Exception",exception.toString());

        //  Crashlytics.logException(exception);
    }

    // Logs non-fatal exception to the firebase crashlytics with key error-type
    public static void logException(Throwable exception,String errorType,String errorValue){
        Log.d("Exception",exception.toString());
      //  Crashlytics.setString(errorType, errorValue);
       // Crashlytics.logException(exception);
    }
}
