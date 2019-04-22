package com.scarlett.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
        super.onCreate(savedInstanceState);
        loadDashBoardFragment();
        mBottomNavBar.setmIOnBottomBarItemClickListener(this);

    }

    public void loadDashBoardFragment(){
        doChangeFragment(DashBoardFragment.newInstance(), DashBoardFragment.TAG, false);
    }

   /* public void loadProfileFragment(View view) {
        doChangeFragment(MyAccountFragment.newInstance(), MyAccountFragment.TAG, false);
    }

    public void loadVideoFragment(View view) {
        doChangeFragment(VideoFragment.newInstance(), VideoFragment.TAG, false);
    }

    public void loadGalleryFragment(View view) {
        doChangeFragment(GalleryFragment.newInstance(), GalleryFragment.TAG, false);
    }*/

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

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_option);

        // set the custom dialog components - text, image and button
        TextView txt_gallery = (TextView) dialog.findViewById(R.id.txt_gallery);
        TextView txt_video = (TextView) dialog.findViewById(R.id.txt_video);

        txt_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        txt_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();

    }


    @Override
    public void onBackPressed() {
      /*  if (mTAG.equals(GalleryFragment.TAG) || mTAG.equals(VideoFragment.TAG)
                || mTAG.equals(MyAccountFragment.TAG)){
            loadDashBoardFragment();
        }else {
            super.onBackPressed();
            finish();
        }*/

        super.onBackPressed();
        finish();
    }

    public void openDialog(View view) {
        createVideoGallery();
    }
}
