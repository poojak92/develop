package com.scarlett.Utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.widget.Toast;

import com.scarlett.activity.LoginActivity;
import com.scarlett.constants.AppConstants;
import com.scarlett.core.ScarletApplication;
import com.scarlett.helper.Router;
import com.scarlett.manager.SharedPreferencesManager;


public class CommanUtils {
    public static Context mContext;
    private AppCompatActivity mCurrentActivityWindow;
    private static TextWatcher mNameOnCardTextWatcher;
    public static boolean isActivityRecreated = false;


    public CommanUtils(AppCompatActivity currentActivityWindow) {
        this.mCurrentActivityWindow = currentActivityWindow;
    }
    public CommanUtils(Context context){
        this.mContext = context;
    }

    //Network Check
    public static boolean isNetworkAvailable() {
        Context mContext = ScarletApplication.getContext();
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }





    public void doLogoutLocally() {
        SharedPreferencesManager.delete(AppConstants.Login.USERLOGIN);
        new Router(mCurrentActivityWindow).startActivityClearTop(LoginActivity.class,true,true);
    }



    public String setVersionCode() {
        PackageInfo packageInfo = null;
        String versionName="";
        try {
            packageInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo != null) {
            versionName=packageInfo.versionName;
        }
        return versionName;
    }



    public int getDpInPx(int dimensId){
        float dp = mCurrentActivityWindow.getResources().getDimensionPixelSize(dimensId);
        Resources r = mCurrentActivityWindow.getResources();
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                r.getDisplayMetrics()
        );

        return mCurrentActivityWindow.getResources().getDimensionPixelSize(dimensId);
    }

    public void showToast(Context mContext,String message){
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }
}
