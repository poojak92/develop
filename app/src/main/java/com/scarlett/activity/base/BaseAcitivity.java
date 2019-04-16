package com.scarlett.activity.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.scarlett.helper.Router;

import butterknife.ButterKnife;

public abstract class BaseAcitivity extends AppCompatActivity {
    protected Router mRouter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mRouter = new Router(this);
    }

    public Router getRouter() {
        return mRouter;
    }

}
