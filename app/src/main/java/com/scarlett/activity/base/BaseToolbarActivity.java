package com.scarlett.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.scarlett.R;
import com.scarlett.Ui.CustomeView.CustomTextView;

import butterknife.BindView;
import butterknife.OnClick;

public abstract class BaseToolbarActivity extends BaseAcitivity {

    @BindView(R.id.ib_toolbar_left)
    ImageButton ibToolbarLeft;

    @BindView(R.id.ib_toolbar_right)
    ImageView ibToolbarRight;

    @BindView(R.id.ib_toolbar_notification)
    ImageView ibToolbarNotification;



    @BindView(R.id.tv_toolbar_title)
    CustomTextView tvToolbarTitle;

    @Nullable
    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;

    @BindView(R.id.cl_toolbar_parent)
    ConstraintLayout mClToolbarParent;

    @BindView(R.id.cl_white_title_layout)
    ConstraintLayout mClWhiteTitleLayout;
    @BindView(R.id.tv_toolbar_white_title)
    TextView mTvWhiteTitle;
    @BindView(R.id.tv_toolbar_subtitle)
    TextView mTvToolbarSubtitle;
    @BindView(R.id.view_toolbar_shadow)
    View mViewToolbarShadow;


    private RightClickListener mRightClickListener;
    private ILeftClickListener mLeftClickListener;
    private NotificationClickListener mNotificationClickListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showLeftButton(final int id, ILeftClickListener leftClickListener) {
        if (ibToolbarLeft != null) {
            ibToolbarLeft.setVisibility(View.VISIBLE);
            ibToolbarLeft.setImageResource(id);
            this.mLeftClickListener =  leftClickListener;
        }
    }

    public void showRightButton(final int id, RightClickListener rightClickListener) {
        if (ibToolbarRight != null) {
            ibToolbarRight.setVisibility(View.VISIBLE);
            ibToolbarRight.setImageDrawable(getResources().getDrawable(R.drawable.ic_search));
            this.mRightClickListener = rightClickListener;
        }

    }
    public void showNotificationButton(final int id, NotificationClickListener notificationClickListener) {
        if (ibToolbarNotification != null) {
            ibToolbarNotification.setVisibility(View.VISIBLE);
            this.mNotificationClickListener = notificationClickListener;
        }

    }

    public void showTitle(String title) {
        if (tvToolbarTitle != null) {
            tvToolbarTitle.setVisibility(View.VISIBLE);
            tvToolbarTitle.setText(title);
            mClWhiteTitleLayout.setVisibility(View.GONE);

        }
    }

    public void hideLeftButton() {
        if (ibToolbarLeft != null) {
            ibToolbarLeft.setVisibility(View.GONE);
        }
    }

    public void hideRightButton() {
        if (ibToolbarRight != null) {
            ibToolbarRight.setVisibility(View.GONE);
        }
    }

    /*Interface*/
    public interface ILeftClickListener {
        void onLeftButtonClicked();
    }

    public interface RightClickListener {
        void onRightButtonClicked();
    }

    public interface NotificationClickListener {
        void onNotificationButtonClicked();
    }

    @OnClick(R.id.ib_toolbar_left)
    public void onClickLeftImage(){
        if (mLeftClickListener != null) {
            mLeftClickListener.onLeftButtonClicked();
        }

    }

    @OnClick(R.id.ib_toolbar_right)
    public void onClickRightImage(){
        if (mRightClickListener != null) {
            mRightClickListener.onRightButtonClicked();
        }
    }
    @OnClick(R.id.ib_toolbar_notification)
    public void onClickNotificationImage(){
        if (mNotificationClickListener != null) {
            mNotificationClickListener.onNotificationButtonClicked();
        }
    }

    public void showTitle(int titleResourceId) {
        if (tvToolbarTitle != null) {
            tvToolbarTitle.setVisibility(View.VISIBLE);
            tvToolbarTitle.setText(getString(titleResourceId));
            mClWhiteTitleLayout.setVisibility(View.GONE);
        }
    }

    public void showToolabr(){
        mClToolbarParent.setVisibility(View.VISIBLE);
    }

    public void hideToolbar(){
        mClToolbarParent.setVisibility(View.GONE);
    }

    public void showWhiteTextToolbar(final String title,final String subtitle){

        if(mClWhiteTitleLayout != null){
            mClWhiteTitleLayout.setVisibility(View.VISIBLE);
            tvToolbarTitle.setVisibility(View.GONE);

            if(mTvWhiteTitle != null && title != null){
                mTvWhiteTitle.setText(title);
            }

            if(mTvToolbarSubtitle != null && subtitle != null){
                mTvToolbarSubtitle.setText(subtitle);
            }
        }
    }

    public void setToolbarShadowVisibility(int visibility){
        mViewToolbarShadow.setVisibility(visibility);
    }
}
