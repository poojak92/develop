package com.scarlett.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.scarlett.Utils.BottomBarSingleton;
import com.scarlett.activity.base.BaseAcitivity;
import com.scarlett.R;
import com.scarlett.constants.AppConstants;
import com.scarlett.manager.SharedPreferencesManager;

public class SplashActivity extends BaseAcitivity {

    private final int SECONDS_TO_SHOW_CARD = 3;
    private Handler mTimeHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public void init()
    {
        setContentView(R.layout.activity_splash);
        setTimer();
        BottomBarSingleton.getInstance(SplashActivity.this).setBottomData();

    }

    public void setTimer() {
        int mTimerTime = SECONDS_TO_SHOW_CARD * 1000;
        mTimeHandler = new Handler();
        mTimeHandler.postDelayed(timerRunnable, mTimerTime);
    }

    public Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            boolean userlogin = SharedPreferencesManager.getBoolean(AppConstants.Login.USERLOGIN,false);
            if(userlogin) {
                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();

            }else {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

            }

    };


}
