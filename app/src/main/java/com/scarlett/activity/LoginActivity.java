package com.scarlett.activity;

import android.os.Bundle;
import android.view.View;

import com.scarlett.R;
import com.scarlett.activity.base.BaseAcitivity;

public class LoginActivity extends BaseAcitivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);

    }

    public void startActivity(View view) {

        mRouter.startActivity(RegisterActivity.class);
    }
}
