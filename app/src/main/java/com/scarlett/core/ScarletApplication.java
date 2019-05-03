package com.scarlett.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatDelegate;



public class ScarletApplication extends MultiDexApplication {

    private static Context mContext;
    public static final String LOG_TAG = "AppAuthSample";

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public ScarletApplication() {

    }

    public static Context getContext() {
        return mContext;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
       // Fabric.with(this, new Crashlytics());

        //Checking app is in background or screen on/off
        final LifeCycleHandler lifeCycleHandler = new LifeCycleHandler(this);
        registerComponentCallbacks(lifeCycleHandler);
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                  //  lifeCycleHandler.signalBackground();
                }
            }
        }, intentFilter);
    }
}
