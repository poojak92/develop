package com.scarlett.core;

import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;

import com.scarlett.constants.AppConstants;
import com.scarlett.manager.BroadcastManager;


/**
 * Created by rac on 4/1/19.
 */

public class LifeCycleHandler implements ComponentCallbacks2 {
    public static final String TAG = LifeCycleHandler.class.getSimpleName();
    public static boolean sendSignal = true;
    private static boolean isApplicationInBackground = false;
    private static Context mContext;

    public LifeCycleHandler(Context context) {
        mContext = context;
    }

    @Override
    public void onTrimMemory(int level) {
       //the process had been showing a user interface, and is no longer doing so   //the process is nearing the end of the background
        if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN || level == ComponentCallbacks2.TRIM_MEMORY_COMPLETE) {
            isApplicationInBackground = true;
          //  signalBackground();

        }

    }

  /*  public void signalBackground(){
        if (sendSignal){
            BroadcastManager.sendLocalBroadcast(AppConstants.Broadcast.BROADCAST_APPLICATION_IN_BACKGROUND);
        }
    }*/

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

    }

    @Override
    public void onLowMemory() {

    }
}
