package com.scarlett.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.scarlett.Fragment.DashBoardFragment;
import com.scarlett.Fragment.ProfileFragment;
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
        mTitle.setText(getResources().getString(R.string.app_name));

    }

    public void loadDashBoardFragment(){
        doChangeFragment(DashBoardFragment.newInstance(), DashBoardFragment.TAG, false);
    }


    public void loadProfileFragment(View view) {
        doChangeFragment(ProfileFragment.newInstance(), ProfileFragment.TAG, false);
    }
}
