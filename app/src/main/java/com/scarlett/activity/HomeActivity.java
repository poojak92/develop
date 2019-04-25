package com.scarlett.activity;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.TextView;

import com.scarlett.Callback.Fragment.FragmentPresenter;
import com.scarlett.Fragment.DashBoardFragment;
import com.scarlett.Fragment.GalleryFragment;
import com.scarlett.Fragment.MyAccountFragment;
import com.scarlett.Fragment.VideoFragment;
import com.scarlett.R;
import com.scarlett.Ui.CustomeView.CustomBottomNavBar;
import com.scarlett.activity.base.BaseBackstackManagerActivity;
import com.scarlett.constants.AppConstants;

import butterknife.BindView;

public class HomeActivity extends BaseBackstackManagerActivity implements FragmentPresenter, CustomBottomNavBar.IOnBottomBarItemClickListener {



    @BindView(R.id.tv_toolbar_white_title)
    TextView mTitle;
    String mTAG= AppConstants.BottomBar.ViewTags.VIEW_TAG_HOME;
    @BindView(R.id.bottom_bar)
    CustomBottomNavBar mBottomNavBar;

    @BindView(R.id.clCustomOption)
    ConstraintLayout mClCustomOption;

    boolean flagClick = false;
    String mClickedTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
        super.onCreate(savedInstanceState);
        loadDashBoardFragment();
        mBottomNavBar.setmIOnBottomBarItemClickListener(this);
        showRightButton(R.id.ib_toolbar_right, new RightClickListener() {
            @Override
            public void onRightButtonClicked() {
                mRouter.startActivity(NotificationActivity.class);
            }
        });
    }

    public void loadDashBoardFragment(){
        doChangeFragment(DashBoardFragment.newInstance(), DashBoardFragment.TAG, false);
    }

    public void loadProfileFragment() {
        doChangeFragment(MyAccountFragment.newInstance(), MyAccountFragment.TAG, false);
    }

    public void loadVideoFragment() {
        doChangeFragment(VideoFragment.newInstance(), VideoFragment.TAG, false);
    }

    public void loadGalleryFragment() {
        doChangeFragment(GalleryFragment.newInstance(), GalleryFragment.TAG, false);
    }

    @Override
    public void passFragmentTag(String TAG) {
        mTAG=TAG;
        if(TAG.equals(DashBoardFragment.TAG)) {
            showToolabr();
            showTitle(getResources().getString(R.string.app_name));
            showRightButton(R.id.ib_toolbar_right, new RightClickListener() {
                @Override
                public void onRightButtonClicked() {

                    mRouter.startActivity(SearchCreaterActivity.class);
                }
            });
            showNotificationButton(R.id.ib_toolbar_notification, new NotificationClickListener() {
                @Override
                public void onNotificationButtonClicked() {
                    mRouter.startActivity(NotificationActivity.class);
                }
            });


        }
        else if(TAG.equals(GalleryFragment.TAG)) {
            showToolabr();
        }
        else if(TAG.equals(VideoFragment.TAG)) {
            showToolabr();
        }
        else if(TAG.equals(MyAccountFragment.TAG)) {
            hideToolbar();
        }
    }
    @Override
    public void onBottomBarItemClicked(String clickedTag) {
       clearBackStackInclusive();
       clearBackStack();
       mClickedTag=clickedTag;
        switch (clickedTag) {
            case AppConstants.BottomBar.ViewTags.VIEW_TAG_HOME:
                loadDashBoardFragment();
                break;
            case AppConstants.BottomBar.ViewTags.VIEW_TAG_GALLERY:
                loadGalleryFragment();
                break;
            case AppConstants.BottomBar.ViewTags.VIEW_TAG_VIDEO:
                loadVideoFragment();
                break;
            case AppConstants.BottomBar.ViewTags.VIEW_TAG_PROFILE:
                loadProfileFragment();
                break;
        }
    }
    public void createVideoGallery(){
        if(!flagClick){
            flagClick=true;
            mClCustomOption.setVisibility(View.VISIBLE);
        }else {
            flagClick=false;
            mClCustomOption.setVisibility(View.GONE);
        }
    }




    @Override
    public void onBackPressed() {
        if (mTAG.equals(GalleryFragment.TAG) || mTAG.equals(VideoFragment.TAG)
                || mTAG.equals(MyAccountFragment.TAG)){
            loadDashBoardFragment();
        }else {
            super.onBackPressed();
            finish();
        }

       // super.onBackPressed();
        //finish();
    }

    public void openDialog(View view) {
        createVideoGallery();
    }
}
