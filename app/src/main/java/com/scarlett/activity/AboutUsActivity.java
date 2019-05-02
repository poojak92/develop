package com.scarlett.activity;

import android.os.Bundle;

import com.scarlett.R;
import com.scarlett.activity.base.BaseToolbarActivity;

public class AboutUsActivity extends BaseToolbarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_aboutus);
        super.onCreate(savedInstanceState);
        showLeftButton(R.drawable.ic_toolbar_back, new ILeftClickListener() {
            @Override
            public void onLeftButtonClicked() {
                onBackPressed();
            }
        });
        showTitle(getResources().getString(R.string.aboutus));
    }


}