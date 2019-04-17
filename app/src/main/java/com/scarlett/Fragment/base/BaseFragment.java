package com.devere.lumina.fragment.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devere.lumina.activity.base.BaseAcitivity;
import com.devere.lumina.activity.base.BaseBackstackManagerActivity;
import com.devere.lumina.utils.CommanUtils;
import com.devere.lumina.utils.DatabaseUtils;

import butterknife.ButterKnife;


public abstract class BaseFragment extends Fragment {

    protected View mRootView;
    protected BaseBackstackManagerActivity mParentActivity;
    protected DatabaseUtils mDatabaseUtils;
    protected CommanUtils mCommanUtils;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getRootView(), container, false);
        ButterKnife.bind(this, mRootView);
        init();
        return mRootView;
    }

    protected abstract int getRootView();


    protected abstract void init();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mParentActivity = (BaseBackstackManagerActivity) context;
        mDatabaseUtils = new DatabaseUtils(mParentActivity);
        mCommanUtils = new CommanUtils(mParentActivity);
    }


    // Starts activity for result with extras
    public void startActivityForResult(Class nextClassActivity, Bundle extras, int requestCode) {
        Intent i = returnIntent(nextClassActivity);
        if (extras != null) {
            i.putExtras(extras);
        }
        startActivityForResult(i, requestCode);
    }

    // Returns intent object
    public Intent returnIntent(Class nextClassActivity) {
        Intent intent = new Intent(mParentActivity, nextClassActivity);
        return intent;
    }
}
