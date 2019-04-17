package com.devere.lumina.activity.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.devere.lumina.R;
import com.devere.lumina.manager.FragmentBackStackManager;

public abstract class BaseBackstackManagerActivity extends BaseToolbarActivity {

    protected FragmentBackStackManager mFragmentBackStackManager;
    protected FragmentManager mFragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mFragmentBackStackManager = FragmentBackStackManager.getInstance(this);
        mFragmentManager = mFragmentBackStackManager.getSupportFragmentManager();
        super.onCreate(savedInstanceState);
    }

    public FragmentBackStackManager getmFragmentBackStackManager() {
        return mFragmentBackStackManager;
    }

    /* doChangeFragment(InfoFragment.newInstance(), InfoFragment.TAG, true);*/
    public void clearBackStackInclusive() {
        try {

            if (mFragmentBackStackManager.getBackStackEntryCount() > 0) {
                mFragmentBackStackManager.popBackStackInclusive();
                mFragmentBackStackManager.removeAllFragmentsFromTransaction();

            }

        } catch (Exception ex) {

        }
    }

    // TODO Uncomment this function once the fragment holder is declared
    public int doChangeFragment(Fragment f, String tag, boolean addToBackStack) {
        return mFragmentBackStackManager.loadFragment(f, tag, addToBackStack, false, R.id.fragment_holder);
    }

    public void clearBackStack() {
        mFragmentBackStackManager.popBackStackInclusive();
    }


    public boolean isFragmentIsBackStack(String tag){
        return mFragmentBackStackManager.getFragmentByTag(tag) != null ? true : false;
    }

    public void popTillSingleWallet() {
        /*if (isSingleWalletInBackStack() && messageFragmentCommitId != -1)
            mFragmentBackStackManager.popBackStack(messageFragmentCommitId, 0);*/
    }

    public Fragment getFragmentByTag(String tag){
        return mFragmentBackStackManager.getFragmentByTag(tag);
    }

    public String getFragmentTagBelowMessageFragment(){
        String tag;

        int index = mFragmentBackStackManager.getBackStackEntryCount() - 2;
        tag = mFragmentBackStackManager.getFragmentTagByIndex(index);

        return tag;
    }

    public String getFirstFragmentTag(){
        return mFragmentBackStackManager.getFragmentTagByIndex(0);
    }


}
