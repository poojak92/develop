package com.scarlett.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.scarlett.core.ScarletApplication;


/**
 * Created by rac on 1/1/19.
 */

public class BroadcastManager {

    public static final String ACTION_APPLICATION_IN_BACKGROUND = ".actionApplicationInBackground";

    // local broadcast receiver


    public static LocalBroadcastReceiver registerReceiver(String action, LocalBroadcastReceiverCallback callBack) {
        LocalBroadcastReceiver receiver = new LocalBroadcastReceiver(callBack);
        if (action == null) return null;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(action);
        LocalBroadcastManager.getInstance(ScarletApplication.getContext()).registerReceiver(receiver, intentFilter);
        return receiver;
    }

    public static final LocalBroadcastReceiver registerReceiver(String[] actions , LocalBroadcastReceiverCallback callback){
        LocalBroadcastReceiver receiver = new LocalBroadcastReceiver(callback);

        try {

        if (actions == null)
            return null;
        IntentFilter intentFilter = new IntentFilter();
        for (String str :actions){
            intentFilter.addAction(str);
        }
        LocalBroadcastManager.getInstance(ScarletApplication.getContext()).registerReceiver(receiver , intentFilter);
        }
        catch (Exception e){
            // TODO
            //Log.e(AppConstants.ClassNames.DeVereScarletApplication, e.toString());
        }
        return receiver;

    }

    public static void unRegisterReceiver(LocalBroadcastReceiver receiver){
        try {

            LocalBroadcastManager.getInstance(ScarletApplication.getContext()).unregisterReceiver(receiver);
        }catch (Exception e){
            //TODO
            //Log.e(AppConstants.ClassNames.DeVereScarletApplication, e.toString());
        }
    }


    public static void sendLocalBroadcast(String action){
        sendLocalBroadcast(action, null, false);
    }

    public static void sendLocalBroadcast(String action ,Boolean isSticky ){
        sendLocalBroadcast(action ,null, false );
    }

    public static void sendLocalBroadcast(String action , Bundle extra){
       Intent intent = new Intent();
       intent.setAction(action);
       if (extra != null){
           intent.putExtras(extra);
       }
       LocalBroadcastManager.getInstance(ScarletApplication.getContext()).sendBroadcast(intent);
    }

    public static void sendLocalBroadcast(String action , Bundle extra , Boolean isSticky){
        Intent intent = new Intent();
        intent.setAction(action);
        if (extra != null){
            intent.putExtras(extra);
        }if (isSticky)
            LocalBroadcastManager.getInstance(ScarletApplication.getContext()).sendBroadcast(intent);

            //sendStickyBroadcast(intent);  deprecated in api level 21
        else
        LocalBroadcastManager.getInstance(ScarletApplication.getContext()).sendBroadcast(intent);
    }

    private static void sendStickyBroadcast(Intent intent){
        ScarletApplication.getContext().sendStickyBroadcast(intent);
    }

    public static void sendLocalBroadcastWithIntent(String action , Intent intent){
        if (intent != null){
            intent.setAction(action);
            LocalBroadcastManager.getInstance(ScarletApplication.getContext()).sendBroadcast(intent);
        }
    }

    // interface for method onReceive callback
    public interface LocalBroadcastReceiverCallback {

        public void onReceive(Context context, Intent intent);
    }



    public static class LocalBroadcastReceiver extends BroadcastReceiver{
        LocalBroadcastReceiverCallback callBack;


        // blank constructor
        public LocalBroadcastReceiver(){


        }


        // constructor with parameter
        public LocalBroadcastReceiver(LocalBroadcastReceiverCallback callBack){
            this.callBack = callBack;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (callBack != null){
                callBack.onReceive(context , intent);
            }
        }
    }

}
