package com.scarlett.Fragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.scarlett.Fragment.base.BaseFragment;
import com.scarlett.Model.ProfileItem;
import com.scarlett.R;
import com.scarlett.adapter.ProfileAdapter;

import java.util.ArrayList;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends BaseFragment {
    public static String TAG = ProfileFragment.class.getName();
    String[] Menu = {
            "Wallet",
            "Recharge",
            "Withdraw",
            "Transaction",
            "Followers",
            "Share",
            "About us",
            "Logout"
    };

    int[] Menu_imageId = {
            R.drawable.ic_about,
            R.drawable.ic_about,
            R.drawable.ic_about,
            R.drawable.ic_about,
            R.drawable.ic_about,
            R.drawable.ic_about,
            R.drawable.ic_about,
            R.drawable.ic_about
    };

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    ProfileItem profileItem;
    ArrayList<ProfileItem> profileItems;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    protected int getRootView() {
        return R.layout.fragment_profile;
    }


    @Override
    protected void init() {
        setProfileItem();

    }

    public void setProfileItem() {
        profileItems = new ArrayList<>();
        for (int i = 0; i < Menu.length; i++) {
           /* if (Menu[i].equals("Wallet Balance")) {
                profileItem = new ProfileItem(Menu[i], Menu_imageId[i], "200");
            } else {
                profileItem = new ProfileItem(Menu[i], Menu_imageId[i], "");
            }*/
            profileItem = new ProfileItem(Menu[i], Menu_imageId[i], "");

            profileItems.add(profileItem);
        }

        //creating recyclerview adapter
        ProfileAdapter adapter = new ProfileAdapter(mParentActivity, profileItems);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mParentActivity,4);
        mRecyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        mRecyclerView.setAdapter(adapter);
    }
}