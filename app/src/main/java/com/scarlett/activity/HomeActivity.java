package com.scarlett.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.scarlett.Fragment.DashBoardFragment;
import com.scarlett.Fragment.GalleryFragment;
import com.scarlett.Fragment.MyAccountFragment;
import com.scarlett.Fragment.VideoFragment;
import com.scarlett.R;
import com.scarlett.activity.base.BaseBackstackManagerActivity;

import butterknife.BindView;

public class HomeActivity extends BaseBackstackManagerActivity {



    @BindView(R.id.tv_toolbar_white_title)
    TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
        super.onCreate(savedInstanceState);
        loadDashBoardFragment();
    }

    public void loadDashBoardFragment(){
        showToolabr();
        doChangeFragment(DashBoardFragment.newInstance(), DashBoardFragment.TAG, false);
    }

    public void loadProfileFragment(View view) {
        hideToolbar();
        doChangeFragment(MyAccountFragment.newInstance(), MyAccountFragment.TAG, false);
    }

    public void loadVideoFragment(View view) {
        showToolabr();
        doChangeFragment(VideoFragment.newInstance(), VideoFragment.TAG, false);
    }

    public void loadGalleryFragment(View view) {
        showToolabr();
        doChangeFragment(GalleryFragment.newInstance(), GalleryFragment.TAG, false);
    }
}
