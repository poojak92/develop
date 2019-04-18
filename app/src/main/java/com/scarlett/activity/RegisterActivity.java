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

import butterknife.BindView;

public class RegisterActivity extends BaseAcitivity {

    @BindView(R.id.btn_register)
    CustomButton mBtnRegister;

    @BindView(R.id.rl_register)
    RelativeLayout mRlRegister;

    @BindView(R.id.cv_register)
    CardView mCvRegister;


    @BindView(R.id.cl_bottom_view)
    ConstraintLayout mClBottomView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        super.onCreate(savedInstanceState);
        setTreeObserver();

    }

    public void setTreeObserver() {
        mRlRegister.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                mRlRegister.getWindowVisibleDisplayFrame(r);
                final int screenHeight = mRlRegister.getRootView().getHeight();
                final int keypadHeight = screenHeight - r.bottom;
                if (keypadHeight > screenHeight * 0.15) {
                    // Keyboard opened
                    mClBottomView.setVisibility(View.GONE);
                    mCvRegister.setContentPadding(0, 0, 0, mBtnRegister.getHeight() + 0);

                } else {
                    // Keyboard closed
                    mClBottomView.setVisibility(View.VISIBLE);
                    mCvRegister.setContentPadding(0, 0, 0, 0);
                }
            }
        });
    }

    public void startActivity(View view) {
        mRouter.startActivity(HomeActivity.class);
    }
}
