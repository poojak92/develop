package com.scarlett.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.scarlett.R;
import com.scarlett.Ui.CustomeView.CustomButton;
import com.scarlett.activity.base.BaseAcitivity;
import com.scarlett.activity.base.BaseToolbarActivity;

import butterknife.BindView;

public class NotificationActivity extends BaseToolbarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_notification);
        super.onCreate(savedInstanceState);
        showLeftButton(R.drawable.ic_toolbar_back, new BaseToolbarActivity.ILeftClickListener() {
            @Override
            public void onLeftButtonClicked() {
                onBackPressed();
            }
        });
        showTitle(getResources().getString(R.string.notificaton));
    }


}
