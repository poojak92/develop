package com.scarlett.Fragment;

import android.support.v4.app.Fragment;
import android.widget.ScrollView;

import com.scarlett.Fragment.base.BaseFragment;
import com.scarlett.R;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends BaseFragment {
    public static String TAG = ProfileFragment.class.getName();
    String[] Menu = {
            "My Post",
            "My Favourite",
            "Wallet Balance",
            "Find Creator",
            "Share",
            "Logout"
    };

    int[] Menu_imageId = {
            R.drawable.ic_post,
            R.drawable.ic_favourite,
            R.drawable.ic_wallet,
            R.drawable.ic_findcreator,
            R.drawable.ic_share,
            R.drawable.ic_logout
    };


    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    protected int getRootView() {
        return R.layout.fragment_dashboard;
    }


    @Override
    protected void init() {




    }


}